package com.ncs.util;
 
import java.util.Properties;
import java.util.ResourceBundle;
 
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 
public class EmailUtility {
 
    static ResourceBundle rb = ResourceBundle
            .getBundle("com.ncs.bundle.system");
 
    private static final String SMTP_HOST_NAME = rb.getString("smtp.server");
 
    private static final String SMTP_PORT = rb.getString("smtp.port");
 
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
 
    private static final String emailFromAddress = rb.getString("email.login");
 
    private static final String emailPassword = rb.getString("email.pwd");
 
 
    private static Properties props = new Properties();

    static {
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
    }
 
    public static void sendMail(EmailMessage emailMessageDTO)
            throws Exception {
 
        try {
 
            // Connection to Mail Server
            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(emailFromAddress,
                                    emailPassword);
                        }
                    });
 
            // Make debug mode true to display debug messages at console
            session.setDebug(true);
 
            // Create a message
            Message msg = new MimeMessage(session);
            InternetAddress addressFrom = new InternetAddress(emailFromAddress);
            msg.setFrom(addressFrom);
 
            // Set TO addresses
            String[] emailIds = new String[0];
 
            if (emailMessageDTO.getTo() != null) {
                emailIds = emailMessageDTO.getTo().split(",");
            }
 
            InternetAddress[] addressTo = new InternetAddress[emailIds.length];
 
            for (int i = 0; i < emailIds.length; i++) {
                addressTo[i] = new InternetAddress(emailIds[i]);
            }
 
           
            if (addressTo.length > 0) {
                msg.setRecipients(Message.RecipientType.TO, addressTo);
            }
 
            // Setting the Subject and Content Type
            msg.setSubject(emailMessageDTO.getSubject());
 
            // Set message MIME type
            switch (emailMessageDTO.getMessageType()) {
            case EmailMessage.HTML_MSG:
                msg.setContent(emailMessageDTO.getMessage(), "text/html");
                break;
            case EmailMessage.TEXT_MSG:
                msg.setContent(emailMessageDTO.getMessage(), "text/plain");
                break;
 
            }
 
            // Send the mail
            Transport.send(msg);
 
        } catch (Exception ex) {
            throw new Exception("Email " + ex.getMessage());
        }
    }
} 