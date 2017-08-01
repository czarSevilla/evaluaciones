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
import czar.evaluaciones.controllers.UserController;
import czar.evaluaciones.dtos.UserDto;
import czar.evaluaciones.services.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {UserController.class})
@ContextConfiguration(classes={Application.class, MvcConfig.class})
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	private HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository;
	
	private CsrfToken csrfToken;
	
	@Before
	public void init() {
		httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
		UserDto value = new UserDto();
		when(userService.find(any(UserDto.class))).thenReturn(value);
		when(userService.getById(eq(1L))).thenReturn(value);
		csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
	}
	
	@Test
	public void list() throws Exception {
		MockHttpServletRequestBuilder request = post("/admin/usuarios/")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Usuarios")));
	}
	
	@Test
	public void addFormTest() throws Exception {
		
		MockHttpServletRequestBuilder request = get("/admin/usuarios/agregar")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Agregar")));
			
	}
	
	@Test
	public void saveTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/admin/usuarios/agregar")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"));
	}
	
	@Test
	public void editFormTest() throws Exception {
		
		MockHttpServletRequestBuilder request = post("/admin/usuarios/editar")
				.requestAttr("_csrf", csrfToken)
				.param("idUser", "1");
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Editar")));
			
	}
	
	@Test
	public void updateTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/admin/usuarios/update")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"));
	}
	
	@Test
	public void deleteTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/admin/usuarios/borrar")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"));
	}
	
	@Test
	public void resetTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/admin/usuarios/reset")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"));
	}
}
