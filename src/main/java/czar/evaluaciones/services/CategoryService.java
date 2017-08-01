package czar.evaluaciones.services;

import java.util.List;

import czar.evaluaciones.dtos.CategoryCloudDto;
import czar.evaluaciones.dtos.CategoryDto;
import czar.evaluaciones.entities.Category;
import czar.evaluaciones.exceptions.ServiceException;

public interface CategoryService {
    
    CategoryDto list(CategoryDto categoryDto);
    
    void save(Category category) throws ServiceException;
    
    Category findById(Long id);
    
    void delete(Long id) throws ServiceException;
    
    List<Category> findByDescription(String descriptionLike);
    
    List<CategoryCloudDto> listCategoryCloud();
}
