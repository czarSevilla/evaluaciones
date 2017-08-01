package czar.evaluaciones.controller.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import czar.evaluaciones.Application;
import czar.evaluaciones.MvcConfig;
import czar.evaluaciones.controllers.QuestionController;
import czar.evaluaciones.dtos.QuestionDto;
import czar.evaluaciones.services.CategoryService;
import czar.evaluaciones.services.QuestionService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {QuestionController.class})
@ContextConfiguration(classes={Application.class, MvcConfig.class})
public class QuestionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private QuestionService questionService;
	
	@MockBean
	private CategoryService categoryService;
	
	private HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository;
	
	private CsrfToken csrfToken;
	
	@Before
	public void init() {
		httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
		csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
		QuestionDto value = new QuestionDto();
		when(questionService.getById(eq(1L))).thenReturn(value);
		when(questionService.find(any(QuestionDto.class))).thenReturn(value);
	}
	
	@Test
	public void listTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/manager/preguntas")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("manager/preguntas/list"));
	}
	
	@Test
	public void addFormTest() throws Exception {
		
		MockHttpServletRequestBuilder request = get("/manager/preguntas/agregar")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("manager/preguntas/add"));
			
	}
	
	@Test
	public void saveTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/manager/preguntas/agregar")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"));
	}
	
	@Test
	public void editFormTest() throws Exception {
		
		MockHttpServletRequestBuilder request = post("/manager/preguntas/editar")
				.requestAttr("_csrf", csrfToken)
				.param("idQuestion", "1");
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Editar")));
			
	}
	
	@Test
	public void updateTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/manager/preguntas/update")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"));
	}
	
	@Test
	public void cambiarTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/manager/preguntas/cambiar")
				.requestAttr("_csrf", csrfToken)
				.param("idQuestion", "1")
				.param("status", "ACTIVA");
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"));
	}
}
