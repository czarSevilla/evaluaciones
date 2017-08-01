package czar.evaluaciones.service.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import czar.evaluaciones.dtos.UserDto;
import czar.evaluaciones.entities.User;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.repositories.AuthorityRepository;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.repositories.NotificationRepository;
import czar.evaluaciones.repositories.UserRepository;
import czar.evaluaciones.services.UserService;
import czar.evaluaciones.services.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
public class UserServiceTest {

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
    	String password = "pwd";
        User user = newUser("username", password, password);
        user.addAuthority("ROLE_USER");
        when(userRepository.findOne(anyLong())).thenReturn(user);
        Set<User> set = new HashSet<>();
        set.add(user);
        when(userRepository.findByActiveRoles(anyVararg())).thenReturn(set);
    }
    
    @Test
    public void saveTest() throws ServiceException {
        String password = "Password1#";
        UserDto user = newUser("admin", password, password);
        
        userService.save(user);
        
        verify(userRepository, atLeastOnce()).save(any(User.class));
    }
    
    @Test
    public void noMatchPasswordTest() {
        Exception exception = null;
        String pass1 = "pass1";
        String pass2 = "pass2";
        
        
        try {
            UserDto user = newUser("admin", pass1, pass2);
            
            userService.save(user);
        } catch (Exception ex) {
            exception = ex;
        }
        
        assertThat(exception.getMessage(), containsString("no coincide"));
    }
    
    @Test
    public void invalidPasswordTest() {
        Exception exception = null;
        String pass1 = "pass1";
        String pass2 = "pass1";
        
        
        try {
            UserDto user = newUser("admin", pass1, pass2);
            
            userService.save(user);
        } catch (Exception ex) {
            exception = ex;
        }
        
        assertThat(exception.getMessage(), containsString("tener una longitud"));
    }
    
    @Test
    public void editUserTest() throws ServiceException {
    	String password = "Password1#";
        UserDto user = newUser("admin", password, password);
        user.setIdUser(1L);
        
        userService.save(user);
        
        verify(userRepository, atLeastOnce()).save(any(User.class));
    }
    
    @Test
    public void deleteUserTest() throws ServiceException {
    	
    	userService.delete(1L);
    	
    	verify(userRepository, atLeastOnce()).delete(anyLong());
    }
    
    @Test
    public void loadUserTest() {
    	
    	userService.loadUserByUsername("username");
    	
    	verify(userRepository, atLeastOnce()).findByUsername(anyString());
    }
    
    @Test
    public void getByIdTest() {
    	
    	UserDto dto = userService.getById(1L);
    	
    	assertThat(dto, notNullValue());
    }
    
    @Test
    public void findByActiveRolesTest() {
    	
    	userService.findByActiveRoles("ROLE_USER");
    	
    	verify(userRepository, atLeastOnce()).findByActiveRoles(anyString());
    }

    
    private UserDto newUser(String username, String password, String passVerify) {
        UserDto dto = new UserDto();
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setVerifyPassword(passVerify);
        return dto;
    }
}
