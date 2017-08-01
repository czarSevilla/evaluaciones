package czar.evaluaciones.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import czar.evaluaciones.dtos.CategoryCloudDto;
import czar.evaluaciones.dtos.CategoryDto;
import czar.evaluaciones.entities.Category;
import czar.evaluaciones.enums.Config;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.repositories.CategoryCustomRepository;
import czar.evaluaciones.repositories.CategoryRepository;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.services.CategoryService;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ConfigurationRepository configurationRepository;
    
    @Autowired
    private CategoryCustomRepository categoryCustomRepository;
    
    @Override
    public CategoryDto list(CategoryDto form) {
        String dirStr = Direction.DESC.toString();
        int numPage = 0;
        int size = Integer.parseInt(configurationRepository.findByKey(Config.ROWS_X_PAGE.toString()).getValue());
        if (form.getDirection() != null) {
            dirStr = form.getDirection();
        }
        if (form.getPage() > 0) {
            numPage = form.getPage();
        }
        if (form.getSize() > 0) {
            size = form.getSize();
        }
        Direction direction = Direction.valueOf(dirStr);
        Sort sort = new Sort(direction, "description");
        Pageable pageable = new PageRequest(numPage, size, sort);
        Page<Category> page = null;
        if (form.getDescription() == null || StringUtils.isEmpty(form.getDescription())) {
            page = categoryRepository.findAll(pageable);
        } else {
            page = categoryRepository.findByDescriptionContainingIgnoreCase(form.getDescription(), pageable);
        }
        CategoryDto result = new CategoryDto();
        result.setCategories(page.getContent());
        result.setDescription(form.getDescription());
        result.setDirection(dirStr);
        result.setPage(page.getNumber());
        result.setSize(page.getSize());
        result.setTotalPages(page.getTotalPages());
        result.setTotalItems(page.getTotalElements());
        return result;
    }

    @Override
    public void save(Category category) throws ServiceException {
    	try {
    		categoryRepository.save(category);
    	} catch (DataIntegrityViolationException dve) {
    		throw new ServiceException("Ya existe un registro con la misma descripci\u00F3n");
    	}
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public void delete(Long id) throws ServiceException {
    	try {
    		categoryRepository.delete(id);
    	} catch (DataIntegrityViolationException dve) {
    		throw new ServiceException("El registro tiene dependencias que impiden su eliminaci\u00F3n");
    	}
    }

	@Override
	public List<Category> findByDescription(String descriptionLike) {
		return categoryRepository.findByDescriptionContainingIgnoreCase(descriptionLike);
	}

	@Override
	public List<CategoryCloudDto> listCategoryCloud() {
		int minCount = categoryCustomRepository.getMinCountCategory();
        int maxCount = categoryCustomRepository.getMaxCountCategory();
        int maxSize = Integer.parseInt(configurationRepository.findByKey(Config.MAX_TAG_SIZE.toString()).getValue());
        Map<String, Integer> map = categoryCustomRepository.getCategoryCloud(minCount, maxCount, maxSize);
        List<CategoryCloudDto> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result.add(new CategoryCloudDto(entry.getKey(), entry.getValue()));
        }
        return result;
	}

}
