// package com.Hayati.Reservation.des.Hotels.services.serviceImpl;

// import org.apache.commons.mail.EmailException;
// import org.apache.commons.mail.SimpleEmail;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

// import javax.mail.Message;
// import javax.mail.Session;
// import javax.mail.Transport;
// import javax.mail.internet.InternetAddress;
// import javax.mail.internet.MimeMessage;
// import com.sun.mail.smtp.SMTPTransport;

// import com.Hayati.Reservation.des.Hotels.services.EmailService;

// import java.util.Date;
// import java.util.Properties;

// @Service
// public class EmailServiceImpl implements EmailService {

//     @Value("${spring.mail.host}")
//     private String hostMail;

//     @Value("${spring.mail.port}")
//     private String smtpPort;

//     @Value("${spring.mail.username}")
//     private String mail;

//     @Value("${spring.mail.password}")
//     private String password;

//     @Override
//     public void sendSimpleEmail(String to, String subject, String message) throws EmailException {
//         try {
//             var email = new SimpleEmail();
//             Properties props = new Properties();
//             props.put("mail.smtp.host", hostMail);
//             props.put("mail.smtp.auth", true);
//             props.put("mail.smtp.port", smtpPort);

//             Session session = Session.getInstance(props);
//             Message messageHolder = new MimeMessage(session);

//             messageHolder.setFrom(new InternetAddress(mail));
//             messageHolder.setSubject(subject);
//             messageHolder.setContent(message, "text/html; charset=UTF-8");
//             messageHolder.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
//             messageHolder.setSentDate(new Date());

//             Transport t = session.getTransport("smtp");
//             t.connect(hostMail, mail, password);
//             t.sendMessage(messageHolder, messageHolder.getAllRecipients());
//             t.close();
//         } catch (Exception e) {
//             throw new EmailException("Failed to send email", e);
//         }
//     }

//     @Override
//     public void sendEmail(String to, String subject, String message) throws EmailException {
//         sendSimpleEmail(to, subject, message);
//     }

//     @Override
//     public void sendEmail(String to, String subject, String fullName, String random) throws EmailException {
//         String content = "Hello " + fullName + ", your code is: " + random;
//         sendSimpleEmail(to, subject, content);
//     }
// }
