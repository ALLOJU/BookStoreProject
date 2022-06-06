package com.bridgelabz.bookstoreproject.service;

import com.bridgelabz.bookstoreproject.dto.ResponseDto;
import com.bridgelabz.bookstoreproject.model.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService implements  IEmailService{
    @Override
    public ResponseEntity<ResponseDto> sendMail(Email email) {
        final String fromEmail = "mounika.alloju@gmail.com";
        final String fromPwd = "kupfmxeccqqoohdi";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // TODO Auto-generated method stub
                return new PasswordAuthentication(fromEmail, fromPwd);
            }
        };
        Session session = Session.getInstance(properties, authenticator);

        MimeMessage mail = new MimeMessage(session);

        try {
            mail.addHeader("Content-type", "text/HTML; charset=UTF-8");
            mail.addHeader("format", "flowed");
            mail.addHeader("Content-Transfer-Encoding", "8bit");

            mail.setFrom(new InternetAddress(fromEmail));
            mail.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
            mail.setText(email.getBody(), "UTF-8");
            mail.setSubject(email.getSubject(), "UTF-8");

            Transport.send(mail);

            ResponseDto responseDTO = new ResponseDto(" Sent email ", mail,null);
            return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);

        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ResponseDto responseDTO = new ResponseDto(" ERROR: Couldn't send email", null,null);
        return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public String getLink(String token) {
        return "http://localhost:8081/userregistration/verify/" + token;
    }

    @Override
    public String getOrder(String token) {
        return "http://localhost:8081/order/verify/" + token;
    }
}
