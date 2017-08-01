package czar.evaluaciones.controller.test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

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
import czar.evaluaciones.controllers.ConfigController;
import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.enums.Config;
import czar.evaluaciones.services.ConfigService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {ConfigController.class})
@ContextConfiguration(classes={Application.class, MvcConfig.class})
public class ConfigControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ConfigService configService;
	
	private HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository;
	
	private CsrfToken csrfToken;
	
	@Before
	public void init() {
		httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
		csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
		
		Configuration config = new Configuration();
		config.setIdConfiguration(1L);
		config.setKey(Config.EXAM_MINUTES.toString());
		config.setValue("120");
		when(configService.findAll()).thenReturn(Arrays.asList(config));
		when(configService.findById(eq(1L))).thenReturn(config);
	}
	
	@Test
	public void configTest() throws Exception {
		MockHttpServletRequestBuilder request = get("/admin/configuracion")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("admin/config"));
	}
	
	@Test
	public void editFormTest() throws Exception {
		
		MockHttpServletRequestBuilder request = post("/admin/configuracion/editar")
				.requestAttr("_csrf", csrfToken)
				.param("idConfiguration", "1");
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("admin/configEdit"));
	}
	
	@Test
	public void updateTest() throws Exception {
		MockHttpServletRequestBuilder request = post("/admin/configuracion/update")
				.requestAttr("_csrf", csrfToken);
				
		mockMvc.perform(request).andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"));
	}
}
