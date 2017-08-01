package czar.evaluaciones.service.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
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
public class FailSaveUserTest {
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
    
    @Before
    public void init() {
        doThrow(DataIntegrityViolationException.class).when(userRepository).save(any(User.class));
        doThrow(DataIntegrityViolationException.class).when(userRepository).delete(anyLong());
    }
    
    @Test
    public void saveTest() throws ServiceException {
        String password = "Password1#";
        UserDto user = newUser("admin", password, password);
        ServiceException exception = null;
        try {
        	userService.save(user);
        } catch (ServiceException se) {
        	exception = se;
        }
        
        verify(userRepository, atLeastOnce()).save(any(User.class));
        assertThat(exception, notNullValue());
    }
    
    @Test
    public void deleteTest() {
    	ServiceException exception = null;
    	try {
    		userService.delete(1L);
    	} catch (ServiceException se) {
    		exception = se;
    	}
    	
    	verify(userRepository, atLeastOnce()).delete(anyLong());
        assertThat(exception, notNullValue());
    }
    
    private UserDto newUser(String username, String password, String passVerify) {
        UserDto dto = new UserDto();
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setVerifyPassword(passVerify);
        return dto;
    }
    
    @Test(expected = ServiceException.class)
    public void resetPwdEa1Test() throws Exception {
    	
    	User user = new User();
    	when(userRepository.findOne(eq(20L))).thenReturn(user);
    	doThrow(DataIntegrityViolationException.class).when(notificationRepository).save(any(Notification.class));
    	Configuration config = new Configuration();
        config.setValue("5");
        when(configurationRepository.findByKey(eq("EXPIRATION_DAY"))).thenReturn(config);
        
    	userService.resetPassword(20L, 2L);
    	
    }
}
