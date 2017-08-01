package czar.evaluaciones.service.test;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import czar.evaluaciones.repositories.CategoryRepository;
import czar.evaluaciones.repositories.ExamRepository;
import czar.evaluaciones.services.ComboService;
import czar.evaluaciones.services.impl.ComboServiceImpl;

@RunWith(SpringRunner.class)
public class ComboServiceTest {

	@TestConfiguration
    static class ComboServiceTestConfiguration {
        @Bean
        public ComboService getComboService() {
            return new ComboServiceImpl();
        }
    }
	
	@Autowired
	private ComboService comboService;
	
	@MockBean
    private ExamRepository examRepository;
    
	@MockBean
    private CategoryRepository categoryRepository;
	
	@Test
	public void getComboExamsTest() {
		
		comboService.getComboExams();
		
		verify(examRepository, atLeastOnce()).findAll();
	}
	
	@Test
	public void getComboCategoriesTest() {
		
		comboService.getComboCategories();
		
		verify(categoryRepository, atLeastOnce()).findAll();
	}
}
