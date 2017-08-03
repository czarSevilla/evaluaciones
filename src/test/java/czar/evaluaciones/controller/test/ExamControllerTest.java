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
import czar.evaluaciones.controllers.ExamController;
import czar.evaluaciones.dtos.ExamDto;
import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.enums.Config;
import czar.evaluaciones.services.ComboService;
import czar.evaluaciones.services.ConfigService;
import czar.evaluaciones.services.ExamService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {ExamController.class})
@ContextConfiguration(classes={Application.class, MvcConfig.class})
public class ExamControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ExamService examService;
	
	@MockBean
	private ComboService comboService;
	
	@MockBean
	private ConfigService configService;
	
	private HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository;
	
	private CsrfToken csrfToken;
	
	@Before
	public void init() {
		httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
		csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
		
		ExamDto value = new ExamDto();
		when(examService.find(any(ExamDto.class))).thenReturn(value);
		when(examService.getById(eq(1L))).thenReturn(value);
		
		Configuration configPassValue = new Configuration();
          configPassValue.setKey(Config.PASS_VALUE.toString());
          configPassValue.setValue("60");
          
          Configuration configExamMinutes = new Configuration();
          configExamMinutes.setKey(Config.EXAM_MINUTES.toString());
          configExamMinutes.setValue("120");
          
          when(configService.findByKey(eq(Config.PASS_VALUE.toString()))).thenReturn(configPassValue);
          when(configService.findByKey(eq(Config.EXAM_MINUTES.toString()))).thenReturn(configExamMinutes);
	}
	
	@Test
	public void listTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/manager/examenes")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("manager/examenes/list"));
	}
	
	@Test
	public void addFormTest() throws Exception {
		
		MockHttpServletRequestBuilder request = get("/manager/examenes/agregar")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Agregar")));
			
	}
	
	@Test
	public void saveTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/manager/examenes/agregar")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"));
	}
	
	@Test
	public void editFormTest() throws Exception {
		
		MockHttpServletRequestBuilder request = post("/manager/examenes/editar")
				.requestAttr("_csrf", csrfToken)
				.param("idExam", "1");
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Editar")));
			
	}
	
	@Test
	public void updateTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/manager/examenes/update")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"));
	}
	
	@Test
	public void deleteTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/manager/examenes/borrar")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"));
	}
}
