package czar.evaluaciones.controller.test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import czar.evaluaciones.WebSecurityConfig;
import czar.evaluaciones.controllers.MainController;
import czar.evaluaciones.dtos.UserDto;
import czar.evaluaciones.entities.User;
import czar.evaluaciones.services.EvaluationService;
import czar.evaluaciones.services.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {MainController.class}, secure = true)
@ContextConfiguration(classes={Application.class, MvcConfig.class, WebSecurityConfig.class})
public class MainControllerTest {

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
		
		UserDto value = new UserDto();
		value.setName("name");
		value.addAuthority("ROLE_USER");
		value.setAuthority("ROLE_USER");
		when(userService.getById(eq(1L))).thenReturn(value);
	}
	
	@Test
	public void indexUserTest() throws Exception {
		User user = new User();
		user.addAuthority("ROLE_USER");
		
		MockHttpServletRequestBuilder request = get("/")
				.requestAttr("_csrf", csrfToken)
				.with(user(user));
		
		mockMvc.perform(request).andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("index"));
	}
	
	@Test
	public void indexManagerTest() throws Exception {
		User user = new User();
		user.addAuthority("ROLE_MANAGER");
		
		MockHttpServletRequestBuilder request = get("/")
				.requestAttr("_csrf", csrfToken)
				.with(user(user));
		
		mockMvc.perform(request).andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("manager/index"));
	}
	
	@Test
	public void indexAdminTest() throws Exception {
		User user = new User();
		user.addAuthority("ROLE_ADMIN");
		
		MockHttpServletRequestBuilder request = get("/")
				.requestAttr("_csrf", csrfToken)
				.with(user(user));
		
		mockMvc.perform(request).andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("admin/index"));
	}
	
	@Test
	public void perfilTest() throws Exception {
		User user = new User();
		user.setIdUser(1L);
		user.addAuthority("ROLE_USER");
		
		MockHttpServletRequestBuilder request = get("/perfil")
				.requestAttr("_csrf", csrfToken)
				.with(user(user));
		
		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(view().name("perfil"));
	}
}
