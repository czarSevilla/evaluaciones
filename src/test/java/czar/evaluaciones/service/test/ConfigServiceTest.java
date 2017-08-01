package czar.evaluaciones.service.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.services.ConfigService;
import czar.evaluaciones.services.impl.ConfigServiceImpl;

@RunWith(SpringRunner.class)
public class ConfigServiceTest {
    @TestConfiguration
    static class ConfigServiceTestConfiguration {
        @Bean
        public ConfigService getConfigService() {
            return new ConfigServiceImpl();
        }
    }
    
    @Autowired
    private ConfigService configService;
    
    @MockBean
    private ConfigurationRepository configurationRepository;
    
    @Before
    public void init() {
        Configuration config = new Configuration();
        config.setIdConfiguration(1L);
        
        when(configurationRepository.findOne(eq(1L))).thenReturn(config);
        when(configurationRepository.findAll()).thenReturn(Arrays.asList(config));
        
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Configuration configuration = invocation.getArgumentAt(0, Configuration.class);
                if (configuration.getIdConfiguration().equals(2L)) {
                    throw new DataIntegrityViolationException("");
                }
                return null;
            }
            
        };
        
        doAnswer(answer).when(configurationRepository).save(any(Configuration.class));
    }
    
    @Test
    public void findByIdTest() {
        Configuration config = null;
        Long idConfiguration = 1L;
        
        config = configService.findById(idConfiguration);
        
        assertThat(config.getIdConfiguration(), equalTo(idConfiguration));
        verify(configurationRepository, atLeastOnce()).findOne(anyLong());
    }
    
    @Test
    public void findAllTest() {
        List<Configuration> list = null;
        
        list = configService.findAll();
        
        assertThat(list.get(0).getIdConfiguration(), equalTo(1L));
        verify(configurationRepository, atLeastOnce()).findAll();
    }
    
    @Test
    public void saveTest() throws ServiceException {
        Configuration config = new Configuration();
        config.setIdConfiguration(1L);
        
        configService.save(config);
        
        verify(configurationRepository, atLeastOnce()).save(any(Configuration.class));
    }
    
    @Test(expected = ServiceException.class)
    public void saveEa1Test() throws ServiceException {
        Configuration config = new Configuration();
        config.setIdConfiguration(2L);
        
        configService.save(config);
    }
}
