package czar.evaluaciones.service.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;

import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.entities.Notification;
import czar.evaluaciones.entities.User;
import czar.evaluaciones.enums.Config;
import czar.evaluaciones.enums.NotificationStatus;
import czar.evaluaciones.enums.NotificationType;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.repositories.NotificationRepository;
import czar.evaluaciones.services.NotificationService;
import czar.evaluaciones.services.impl.NotificationServiceImpl;

@RunWith(SpringRunner.class)
public class NotificationServiceTest {

    @TestConfiguration
    static class NotificationServiceTestConfiguration {
        @Bean
        public NotificationService getNotificationService() {
            return new NotificationServiceImpl();
        }
    }
    
    @Autowired
    private NotificationService notificationService;
    
    @MockBean
    private JavaMailSender mailSender;
    
    @MockBean
    private TemplateEngine templateEngine;
    
    @MockBean
    private NotificationRepository notificationRepository;
    
    @MockBean
    private ConfigurationRepository configurationRepository;
    
    @Before
    public void init() {
    	Configuration config = new Configuration();
    	config.setIdConfiguration(1L);
    	config.setKey(Config.URL_SITE.toString());
    	config.setValue("http://www.qacg.evaluaciones.com");
    	
    	when(configurationRepository.findByKey(eq(Config.URL_SITE.toString()))).thenReturn(config);
    	    	
    	Answer<Void> answer = new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				MimeMessagePreparator p = invocation.getArgumentAt(0, MimeMessagePreparator.class);
				try {
					p.prepare(mock(MimeMessage.class));
				} catch (RuntimeException re) {
					
				}
				return null;
			}
		};
		
    	doAnswer(answer).when(mailSender).send(any(MimeMessagePreparator.class));
    }
    
    @Test
    public void sendNotificationTest() {
    	Notification notification = new Notification();
    	notification.setType(NotificationType.EVALUACION);
    	User manager = new User();
    	manager.setEmail("manager@gmail.com");
    	notification.setManager(manager);
    	notification.setEmailApplicant("czar.sevilla@gmail.com");
    	when(notificationRepository.findAllByStatus(eq((NotificationStatus.REGISTRADA)))).thenReturn(Arrays.asList(notification));
    	
    	notificationService.sendNotifications();
    	
    	verify(notificationRepository, atLeastOnce()).findAllByStatus(eq(NotificationStatus.REGISTRADA));
    	verify(configurationRepository, atLeastOnce()).findByKey(eq(Config.URL_SITE.toString()));
    	verify(mailSender, atLeastOnce()).send(any(MimeMessagePreparator.class));
    	verify(notificationRepository, atLeastOnce()).save(any(Notification.class));
    }
    
    @Test
    public void sendNotificationEa1Test() {
    	Notification notification = new Notification();
    	notification.setType(NotificationType.EVALUACION);
    	User manager = new User();
    	manager.setEmail("manager@gmail.com");
    	notification.setManager(manager);
    	notification.setPasswordApplicant("");
    	notification.setEmailApplicant("czar.sevilla@gmail.com");
    	when(notificationRepository.findAllByStatus(eq((NotificationStatus.REGISTRADA)))).thenReturn(Arrays.asList(notification));
    	
    	notificationService.sendNotifications();
    	
    	verify(notificationRepository, atLeastOnce()).findAllByStatus(eq(NotificationStatus.REGISTRADA));
    	verify(configurationRepository, atLeastOnce()).findByKey(eq(Config.URL_SITE.toString()));
    	verify(mailSender, atLeastOnce()).send(any(MimeMessagePreparator.class));
    	verify(notificationRepository, atLeastOnce()).save(any(Notification.class));
    }
    
    @Test
    public void sendNotificationEa2Test() {
    	when(notificationRepository.findAllByStatus(eq((NotificationStatus.REGISTRADA)))).thenReturn(new ArrayList<>());
    	
    	notificationService.sendNotifications();
    }
    
    @Test
    public void sendResetTest() {
    	Notification notification = new Notification();
    	notification.setType(NotificationType.RESET);
    	User manager = new User();
    	manager.setEmail("manager@gmail.com");
    	notification.setManager(manager);
    	notification.setEmailApplicant("czar.sevilla@gmail.com");
    	when(notificationRepository.findAllByStatus(eq((NotificationStatus.REGISTRADA)))).thenReturn(Arrays.asList(notification));
    	
    	notificationService.sendNotifications();
    	
    	verify(notificationRepository, atLeastOnce()).findAllByStatus(eq(NotificationStatus.REGISTRADA));
    	verify(mailSender, atLeastOnce()).send(any(MimeMessagePreparator.class));
    	verify(notificationRepository, atLeastOnce()).save(any(Notification.class));
    }
    
}
