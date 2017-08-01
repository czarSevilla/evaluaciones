package czar.evaluaciones.service.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import czar.evaluaciones.dtos.CategoryDto;
import czar.evaluaciones.entities.Category;
import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.enums.Config;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.repositories.CategoryCustomRepository;
import czar.evaluaciones.repositories.CategoryRepository;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.services.CategoryService;
import czar.evaluaciones.services.impl.CategoryServiceImpl;

@RunWith(SpringRunner.class)
public class CategoryServiceTest {
    
    @TestConfiguration
    static class CategoryServiceTestConfiguration {
        @Bean
        public CategoryService getCategoryService() {
            return new CategoryServiceImpl();
        }
    }
    
    @Autowired
    private CategoryService categoryService;
    
    @MockBean
    private CategoryRepository categoryRepository;
    
    @MockBean
    private ConfigurationRepository configurationRepository;
    
    @MockBean
    private CategoryCustomRepository categoryCustomRepository;
    
    @Before
    public void init() {
        Configuration config = new Configuration();
        config.setValue("5");
        when(configurationRepository.findByKey(Config.ROWS_X_PAGE.toString())).thenReturn(config);
        when(configurationRepository.findByKey(Config.MAX_TAG_SIZE.toString())).thenReturn(config);
        
        Page<Category> page = new Page<Category>() {
            @Override public List<Category> getContent() { return null; }
            @Override public int getNumber() { return 0; }
            @Override public int getNumberOfElements() { return 0; }
            @Override public int getSize() { return 0; }
            @Override public Sort getSort() { return null; }
            @Override public boolean hasContent() { return false; }
            @Override public boolean hasNext() { return false; }
            @Override public boolean hasPrevious() { return false; }
            @Override public boolean isFirst() { return false; }
            @Override public boolean isLast() { return false; }
            @Override public Pageable nextPageable() { return null; }
            @Override public Pageable previousPageable() { return null; }
            @Override public Iterator<Category> iterator() { return null; }
            @Override public long getTotalElements() { return 0; }
            @Override public int getTotalPages() { return 0; }
            @Override public <S> Page<S> map(Converter<? super Category, ? extends S> arg0) { return null; }
        };
        
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(categoryRepository.findByDescriptionContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(page);
        
        Answer<Void> answer = new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Category category = invocation.getArgumentAt(0, Category.class);
                Long idCategoryException = 2L;
                if (idCategoryException.equals(category.getIdCategory())) {
                    throw new DataIntegrityViolationException("");
                }
                return null;
            }
            
        };
        
        doAnswer(answer).when(categoryRepository).save(any(Category.class));
        
        Answer<Void> answerDelete = new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Long idCategory = invocation.getArgumentAt(0, Long.class);
                if (idCategory.equals(2L)) {
                    throw new DataIntegrityViolationException("");
                }
                return null;
            }
            
        };
        
        doAnswer(answerDelete).when(categoryRepository).delete(anyLong());
        
        Map<String, Integer> map = new HashMap<>();
        map.put("Category", 10);
        when(categoryCustomRepository.getCategoryCloud(anyInt(), anyInt(), anyInt())).thenReturn(map);
    }
    
    
    @Test
    public void listTest() {
        CategoryDto categoryDto = new CategoryDto();
        
        categoryService.list(categoryDto);
        
        verify(configurationRepository, atLeastOnce()).findByKey(anyString());
        verify(categoryRepository, atLeastOnce()).findAll(any(Pageable.class));
    }
    
    @Test
    public void listEa1Test() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setDirection("DESC");
        categoryDto.setPage(1);
        categoryDto.setSize(10);
        categoryDto.setDescription("description");
                
        categoryService.list(categoryDto);
        
        verify(configurationRepository, atLeastOnce()).findByKey(anyString());
        verify(categoryRepository, atLeastOnce()).findByDescriptionContainingIgnoreCase(anyString(), any(Pageable.class));
    }
    
    @Test
    public void listEa2Test() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setDirection("DESC");
        categoryDto.setPage(1);
        categoryDto.setSize(10);
        categoryDto.setDescription("");
                
        categoryService.list(categoryDto);
        
        verify(configurationRepository, atLeastOnce()).findByKey(anyString());
        verify(categoryRepository, atLeastOnce()).findAll(any(Pageable.class));
    }
    
    @Test
    public void saveTest() throws ServiceException {
        Category cat = new Category();
        cat.setIdCategory(1L);
        
        categoryService.save(cat);
        
        verify(categoryRepository, atLeastOnce()).save(any(Category.class));
    }
    
    @Test(expected = ServiceException.class)
    public void saveEa1Test() throws ServiceException {
        Category cat = new Category();
        cat.setIdCategory(2L);
        
        categoryService.save(cat);
    }
    
    @Test
    public void findByIdTest() {
        Long id = 1L;
        
        categoryService.findById(id);
        
        verify(categoryRepository, atLeastOnce()).findOne(anyLong());
    }
    
    @Test
    public void deleteTest() throws ServiceException {
        Long id = 1L;
        
        categoryService.delete(id);
        
        verify(categoryRepository, atLeastOnce()).delete(anyLong());
    }
    
    @Test(expected = ServiceException.class)
    public void deleteEa1Test() throws ServiceException {
        Long id = 2L;
        
        categoryService.delete(id);
    }
    
    @Test
    public void findByDescriptionTest() {
        String description = "description";
        
        categoryService.findByDescription(description);
        
        verify(categoryRepository, atLeastOnce()).findByDescriptionContainingIgnoreCase(description);
    }
    
    @Test
    public void listCategoryCloud() {
        
        categoryService.listCategoryCloud();
        
        verify(configurationRepository, atLeastOnce()).findByKey(eq(Config.MAX_TAG_SIZE.toString()));
        verify(categoryCustomRepository, atLeastOnce()).getMinCountCategory();
        verify(categoryCustomRepository, atLeastOnce()).getMaxCountCategory();
        verify(categoryCustomRepository, atLeastOnce()).getCategoryCloud(anyInt(), anyInt(), anyInt());
    }
    
    
}
