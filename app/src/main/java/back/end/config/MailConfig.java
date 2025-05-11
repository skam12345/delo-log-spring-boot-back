package back.end.config;

import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


@Configuration
public class MailConfig {

    private final Environment environment;

    public MailConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.naver.com");
        mailSender.setUsername("edsshjy3@naver.com");
        mailSender.setPassword("!@Xkzhdizl12");
        mailSender.setPort(465);  // Use 587 for TLS
        mailSender.setProtocol("smtp");


        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");  // for TLS, set to true
        properties.put("mail.smtp.ssl.enable", "true");  // for SSL, set to true
        properties.put("mail.debug", "true");

        return mailSender;
    }
}
