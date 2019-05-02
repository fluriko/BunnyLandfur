package mate.academy.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import mate.academy.util.RandomGenerator;
import org.apache.log4j.Logger;
import java.util.Properties;

public class MailService {
    private static final Logger logger = Logger.getLogger(MailService.class);

    public String sendMail(String userEmail) {
        final String username = "bunnyland.fur@gmail.com";
        final String password = "111qqq---";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject("disposable code");
            String randomCode = RandomGenerator.generateCode();
            message.setText("Your disposable code is: " + randomCode);
            Transport.send(message);
            logger.debug("message sent to " + userEmail);
            return randomCode;
        } catch (MessagingException e) {
            logger.error("Error in sending message to " + userEmail, e);
            return "";
        }
    }
}
