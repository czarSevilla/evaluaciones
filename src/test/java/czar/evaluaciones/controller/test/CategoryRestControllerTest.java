package czar.evaluaciones.controller.test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import czar.evaluaciones.Application;
import czar.evaluaciones.MvcConfig;
import czar.evaluaciones.controllers.CategoryRestController;
import czar.evaluaciones.entities.Category;
import czar.evaluaciones.services.CategoryService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {CategoryRestController.class})
@ContextConfiguration(classes={Application.class, MvcConfig.class})
public class CategoryRestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CategoryService categoryService;
	
	private HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository;
	
	private CsrfToken csrfToken;
	
	@Before
	public void init() {
		httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
		csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
		
		Category category = new Category();
		category.setIdCategory(1L);
		category.setDescription("category");
		when(categoryService.findByDescription(eq("category"))).thenReturn(Arrays.asList(category));
	}
	
	@Test
	public void list() throws Exception {
		MockHttpServletRequestBuilder request = get("/manager/categorias/find/category")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}
	
}
