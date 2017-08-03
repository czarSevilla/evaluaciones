package czar.evaluaciones.controller.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;

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
import czar.evaluaciones.controllers.EvaluationController;
import czar.evaluaciones.dtos.EvaluationDto;
import czar.evaluaciones.dtos.PaginadorDto;
import czar.evaluaciones.dtos.ViewEvalDto;
import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.enums.Config;
import czar.evaluaciones.services.ComboService;
import czar.evaluaciones.services.ConfigService;
import czar.evaluaciones.services.EvaluationService;
import czar.evaluaciones.services.UserService;
import czar.evaluaciones.utils.DateUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {EvaluationController.class})
@ContextConfiguration(classes={Application.class, MvcConfig.class})
public class EvaluationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ComboService comboService;
	
	@MockBean
	private EvaluationService evaluationService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private ConfigService configService;
	
	private HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository;
	
	private CsrfToken csrfToken;
	
	@Before
	public void init() {
		httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
		csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
		
		ViewEvalDto page = new ViewEvalDto();
		when(evaluationService.findEvaluations(any(ViewEvalDto.class))).thenReturn(page);
		EvaluationDto dto = new EvaluationDto();
		PaginadorDto paginador = new PaginadorDto();
		paginador.setTotalItems(10);
		paginador.setTotalPages(10);
		dto.setPaginador(paginador);
		dto.setEndTime(DateUtils.addMinutes(new Date(), 120));
		when(evaluationService.loadEvaluation(eq(1L), anyLong(), any(EvaluationDto.class))).thenReturn(dto);
		EvaluationDto dto1 = new EvaluationDto();
		dto1.setError(true);
		when(evaluationService.loadEvaluation(eq(2L), anyLong(), any(EvaluationDto.class))).thenReturn(dto1);
		EvaluationDto dto2 = new EvaluationDto();
		dto2.setFinish(true);
		when(evaluationService.loadEvaluation(eq(3L), anyLong(), any(EvaluationDto.class))).thenReturn(dto2);
		
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
		MockHttpServletRequestBuilder request = get("/manager/evaluaciones")
				.requestAttr("_csrf", csrfToken);
		
		mockMvc.perform(request).andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("manager/evaluaciones/list"));
	}
	
	@Test
	public void generarInitTest() throws Exception {
		MockHttpServletRequestBuilder request = get("/manager/evaluaciones/generar")
				.requestAttr("_csrf", csrfToken);
		
		mockMvc.perform(request).andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("manager/evaluaciones/step1"));
	}
	
	@Test
	public void generarActionTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/manager/evaluaciones/generar")
				.requestAttr("_csrf", csrfToken)
				.param("step", "1")
				.param("source", "CATEGORIES");
		
		mockMvc.perform(request).andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("manager/evaluaciones/step2"));
	}
	
	@Test
	public void generarActionEa1Test() throws Exception {
		MockHttpServletRequestBuilder request = post("/manager/evaluaciones/generar")
				.requestAttr("_csrf", csrfToken)
				.param("step", "1")
				.param("source", "EXAM");
		
		mockMvc.perform(request).andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("manager/evaluaciones/step2"));
	}
	
	@Test
	public void generarActionEa2Test() throws Exception {
		MockHttpServletRequestBuilder request = post("/manager/evaluaciones/generar")
				.requestAttr("_csrf", csrfToken)
				.param("step", "2");
		
		mockMvc.perform(request).andDo(print())
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/manager/evaluaciones"));
	}
	
	@Test
	public void evaluateTest() throws Exception {
		MockHttpServletRequestBuilder request = get("/evaluacion")
				.requestAttr("_csrf", csrfToken);
		
		mockMvc.perform(request).andDo(print())
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"));
	}
	
	@Test
	public void evaluateEa1Test() throws Exception {
		MockHttpServletRequestBuilder request = post("/evaluacion")
				.requestAttr("_csrf", csrfToken)
				.param("idEvaluation", "1");
		
		mockMvc.perform(request).andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("manager/evaluaciones/evaluacion"));
	}
	
	@Test
	public void evaluateEa2Test() throws Exception {
		MockHttpServletRequestBuilder request = post("/evaluacion")
				.requestAttr("_csrf", csrfToken)
				.param("idEvaluation", "2");
		
		mockMvc.perform(request).andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("manager/evaluaciones/evaluacionError"));
	}
	
	@Test
	public void evaluateEa3Test() throws Exception {
		MockHttpServletRequestBuilder request = post("/evaluacion")
				.requestAttr("_csrf", csrfToken)
				.param("idEvaluation", "3");
		
		mockMvc.perform(request).andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("manager/evaluaciones/finish"));
	}
}
