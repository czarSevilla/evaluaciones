package czar.evaluaciones.service.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import czar.evaluaciones.dtos.UserDto;
import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.entities.Notification;
import czar.evaluaciones.entities.User;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.repositories.AuthorityRepository;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.repositories.NotificationRepository;
import czar.evaluaciones.repositories.UserRepository;
import czar.evaluaciones.services.UserService;
import czar.evaluaciones.services.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
public class PasswordUserTest {
	@TestConfiguration
    static class UserServiceTestConfiguration {
        @Bean
        public UserService getUserService() {
            return new UserServiceImpl();
        }
    }
    
    @Autowired
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;
    
    @MockBean
    private PasswordEncoder passwordEncoder;
    
    @MockBean
    private ConfigurationRepository configurationRepository;
    
    @MockBean
    private AuthorityRepository authorityRepository;
    
    @MockBean
    private NotificationRepository notificationRepository;
    
    private static final String VALID_PWD = "Pwd!#$3223rf";
    
    @Before
    public void init() {
    	User user = new User();
    	user.setPassword(VALID_PWD);
    	when(userRepository.findOne(eq(1L))).thenReturn(user);
    	
    	when(passwordEncoder.matches(eq(VALID_PWD), eq(VALID_PWD))).thenReturn(true);
    	when(passwordEncoder.encode(anyString())).thenReturn("@#$FDSF23rfae322qteg23rf");
    	
    	Configuration config = new Configuration();
        config.setValue("5");
        when(configurationRepository.findByKey(eq("EXPIRATION_DAY"))).thenReturn(config);
        
    }
    
    @Test
    public void changePasswordTest() throws ServiceException {
    	UserDto userDto = new UserDto();
    	userDto.setIdUser(1L);
    	userDto.setPassword(VALID_PWD);
    	userDto.setCurrentPassword(VALID_PWD);
    	userDto.setVerifyPassword(VALID_PWD);
    	
    	userService.changePassword(userDto);
    	
    	verify(userRepository, atLeastOnce()).findOne(anyLong());
    	verify(passwordEncoder, atLeastOnce()).matches(anyString(), anyString());
    	verify(passwordEncoder, atLeastOnce()).encode(anyString());
    }
    
    @Test
    public void changePasswordEa1Test() {
    	// Verificar que el password actual no haga match con el password en la bd.
    	UserDto userDto = new UserDto();
    	userDto.setIdUser(1L);
    	userDto.setCurrentPassword("otro");
    	
    	Exception exception = null;
    	try {
    		userService.changePassword(userDto);
    	} catch (ServiceException se) {
    		exception = se;
    	}
    	
    	assertThat(exception, notNullValue());
    	assertThat(exception.getMessage(), containsString("password actual"));
    }
    
    @Test
    public void changePasswordEa2Test() {
    	// Verificar que el nuevo password no coincida con la verificacion
    	UserDto userDto = new UserDto();
    	userDto.setIdUser(1L);
    	userDto.setCurrentPassword(VALID_PWD);
    	userDto.setPassword("val1");
    	userDto.setVerifyPassword("val2");
    	
    	Exception exception = null;
    	try {
    		userService.changePassword(userDto);
    	} catch (ServiceException se) {
    		exception = se;
    	}
    	
    	assertThat(exception, notNullValue());
    	assertThat(exception.getMessage(), containsString("no coincide"));
    }
    
    @Test
    public void changePasswordEa3Test() {
    	// Verificar que el nuevo password no cumple el estandar requerido
    	UserDto userDto = new UserDto();
    	userDto.setIdUser(1L);
    	userDto.setCurrentPassword(VALID_PWD);
    	userDto.setPassword("val1");
    	userDto.setVerifyPassword("val1");
    	
    	Exception exception = null;
    	try {
    		userService.changePassword(userDto);
    	} catch (ServiceException se) {
    		exception = se;
    	}
    	
    	assertThat(exception, notNullValue());
    	assertThat(exception.getMessage(), containsString("al menos"));
    }
    
    @Test
    public void changePasswordEa4Test() {
    	// Cuando ocurre una excepcion al actualizar el password
    	UserDto userDto = new UserDto();
    	userDto.setIdUser(2L);
    	userDto.setPassword(VALID_PWD);
    	userDto.setCurrentPassword(VALID_PWD);
    	userDto.setVerifyPassword(VALID_PWD);
    	
    	User user = new User();
    	user.setPassword(VALID_PWD);
    	when(userRepository.findOne(2L)).thenReturn(user);
    	
    	doThrow(DataIntegrityViolationException.class).when(userRepository).save(user);
    	
    	Exception exception = null;
    	
    	try {
    		userService.changePassword(userDto);
    	} catch (ServiceException se) {
    		exception = se;
    	}
    	
    	assertThat(exception, notNullValue());
    }
    
    @Test
    public void resetPwdTest() throws Exception {
    	
    	userService.resetPassword(1L, 2L);
    	
    	verify(userRepository, atLeastOnce()).findOne(anyLong());
    	verify(userRepository, atLeastOnce()).save(any(User.class));
    	verify(notificationRepository, atLeastOnce()).save(any(Notification.class));
    	
    }
}
