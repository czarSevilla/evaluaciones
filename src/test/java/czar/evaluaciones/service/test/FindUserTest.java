package czar.evaluaciones.service.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import czar.evaluaciones.dtos.UserDto;
import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.entities.User;
import czar.evaluaciones.repositories.AuthorityRepository;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.repositories.NotificationRepository;
import czar.evaluaciones.repositories.UserRepository;
import czar.evaluaciones.services.UserService;
import czar.evaluaciones.services.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
public class FindUserTest {
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
    	Configuration config = new Configuration();
        config.setValue("5");
        when(configurationRepository.findByKey(eq("ROWS_X_PAGE"))).thenReturn(config);
        @SuppressWarnings("unchecked")
		Page<User> page = (Page<User>) mock(Page.class);
        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(userRepository.findByNameContainingIgnoreCase(anyString(), any(Pageable.class)))
        	.thenReturn(page);
    }
    
    @Test
    public void findTest() {
    	UserDto form = new UserDto();
    	
    	UserDto dto = userService.find(form);
    	
    	verify(userRepository, atLeastOnce()).findAll(any(Pageable.class));
    	assertThat(dto, notNullValue());
    	
    }
    
    @Test
    public void findEa1Test() {
    	UserDto form = new UserDto();
    	form.setDirection(Direction.ASC.toString());
    	form.setPage(10);
    	form.setSize(10);
    	form.setName("");
    	
    	UserDto dto = userService.find(form);
    	
    	verify(userRepository, atLeastOnce()).findAll(any(Pageable.class));
    	assertThat(dto, notNullValue());
    	
    }
    
    @Test
    public void findEa2Test() {
    	UserDto form = new UserDto();
    	form.setDirection(Direction.ASC.toString());
    	form.setPage(10);
    	form.setSize(10);
    	form.setName("name");
    	
    	UserDto dto = userService.find(form);
    	
    	verify(userRepository, atLeastOnce()).findByNameContainingIgnoreCase(anyString(), any(Pageable.class));
    	assertThat(dto, notNullValue());
    	
    }
    
}
