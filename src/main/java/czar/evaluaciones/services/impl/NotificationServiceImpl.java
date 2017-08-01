package czar.evaluaciones.services.impl;

import static czar.evaluaciones.enums.Config.URL_SITE;
import static czar.evaluaciones.enums.NotificationType.EVALUACION;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import czar.evaluaciones.entities.Notification;
import czar.evaluaciones.enums.NotificationStatus;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.repositories.NotificationRepository;
import czar.evaluaciones.services.NotificationService;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private TemplateEngine templateEngine;
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private ConfigurationRepository configurationRepository;
    
    private static final String SENDER = "cesar.sevilla@qacg.com";

    @Scheduled(fixedDelay = 60000)
    @Override
    @Transactional
    public void sendNotifications() {
        List<Notification> notifications = notificationRepository.findAllByStatus(NotificationStatus.REGISTRADA);
        
        if (!notifications.isEmpty()) {
        	for (Notification n : notifications) {
            	
            	MimeMessagePreparator preparator = null;
            	
            	if (n.getType() == EVALUACION) {
            	    preparator = prepareEvaluationMail(n);
            	} else {
            	    preparator = prepareResetMail(n);
            	}
            	
                try {
                    mailSender.send(preparator);
                    n.setStatus(NotificationStatus.ENVIADA);
                    notificationRepository.save(n);
                } catch (MailException ex) {
                    logger.error(ex.getMessage());
                }
            }
        }       
    }
    
    private MimeMessagePreparator prepareEvaluationMail(Notification n) {
    	Context context = new Context();
        context.setVariable("name", n.getNameApplicant());
        context.setVariable("username", n.getEmailApplicant());
        context.setVariable("password", n.getPasswordApplicant());
        context.setVariable("urlEvaluation", configurationRepository.findByKey(URL_SITE.toString()).getValue());
        context.setVariable("expirationDate", n.getExpirationDate());
        
        return new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
	            helper.setTo(n.getEmailApplicant());
	            helper.setBcc(n.getManager().getEmail());
	            helper.setSubject("Evaluaci\u00F3n QACG");
	            helper.setFrom(SENDER);
	            if ("".equals(n.getPasswordApplicant())) {
	                helper.setText(templateEngine.process("mail/notification", context), true);
	            } else {
	                helper.setText(templateEngine.process("mail/notificationpwd", context), true);
	            }
			}
		};
        
    }
    
    private MimeMessagePreparator prepareResetMail(Notification n) {
    	Context context = new Context();
        context.setVariable("name", n.getNameApplicant());
        context.setVariable("password", n.getPasswordApplicant());
        
        return new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
	            helper.setTo(n.getEmailApplicant());
	            helper.setBcc(n.getManager().getEmail());
	            helper.setSubject("Reinicio de password");
	            helper.setFrom(SENDER);
	            helper.setText(templateEngine.process("mail/reset", context), true);
			}
		};
        
    }

}
