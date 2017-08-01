package czar.evaluaciones.controller.test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import czar.evaluaciones.controllers.MainController;
import czar.evaluaciones.dtos.UserDto;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.services.EvaluationService;
import czar.evaluaciones.services.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {MainController.class})
@ContextConfiguration(classes={Application.class, MvcConfig.class})
public class CambiarPasswordControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private EvaluationService evaluationService;
    
	@MockBean
    private UserService userService;
	
	private HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository;
	
	private CsrfToken csrfToken;
	
	@Before
	public void init() {
		httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
		csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
	}

	@Test
	public void cambiarPasswordTest() throws Exception {
		
		doNothing().when(userService).changePassword(any(UserDto.class));
		
		MockHttpServletRequestBuilder request = post("/cambiarPassword")
				.requestAttr("_csrf", csrfToken);
		
		mockMvc.perform(request)
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/perfil"))
		.andExpect(flash().attributeExists("message"));
	}
	
	@Test
	public void cambiarPasswordEa1Test() throws Exception {
		
		doThrow(new ServiceException("error test")).when(userService).changePassword(any(UserDto.class));
		
		MockHttpServletRequestBuilder request = post("/cambiarPassword")
				.requestAttr("_csrf", csrfToken);
		
		mockMvc.perform(request)
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/perfil"))
		.andExpect(flash().attributeExists("errorMessage"));
	}
}
