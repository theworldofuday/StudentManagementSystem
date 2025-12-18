package utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    
    // Replace with your Gmail credentials
    private static final String SENDER_EMAIL = "your-email@gmail.com";
    private static final String SENDER_PASSWORD = "your-16-char-app-password";
    
    private static Properties getMailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls. enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail. smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        return props;
    }
    
    public static boolean sendEmail(String toEmail, String subject, String body) {
        try {
            Properties props = getMailProperties();
            
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                }
            });
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            
            Transport.send(message);
            System.out.println("✓ Email sent successfully to " + toEmail);
            return true;
            
        } catch (MessagingException e) {
            System.out.println("⚠ Failed to send email: " + e. getMessage());
            return false;
        }
    }
    
    // HTML Email (looks better)
    public static boolean sendHtmlEmail(String toEmail, String subject, String htmlBody) {
        try {
            Properties props = getMailProperties();
            
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                }
            });
            
            Message message = new MimeMessage(session);
            message. setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message. RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(htmlBody, "text/html; charset=utf-8");
            
            Transport.send(message);
            System.out.println("✓ Email sent successfully to " + toEmail);
            return true;
            
        } catch (MessagingException e) {
            System.out.println("⚠ Failed to send email: " + e. getMessage());
            return false;
        }
    }
}
