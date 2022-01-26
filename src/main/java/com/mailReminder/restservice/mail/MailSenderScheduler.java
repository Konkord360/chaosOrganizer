package com.mailReminder.restservice.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;

@Configuration
@EnableScheduling
public class MailSenderScheduler {

    @Scheduled(fixedDelay = 60000)
    public void test() throws MessagingException, IOException, GeneralSecurityException {
//        Credential credentials = MailService.getCredentials();
//        Gmail gmail = new Gmail.Builder(credentials.getTransport(), credentials.getJsonFactory(), credentials.getRequestInitializer()).build();
//
//        MimeMessage mimeMessage = mailSender.createEmail("konkord720@gmail.com", "chaosorganizercontact@gmail.com", "test", "test");
//        Message message = mailSender.sendMessage(gmail, "me", mimeMessage);
//
////        mailSender.sendMessage(Gmail,"me", mimeMessage);
//
//        System.out.println("regularly printed message ".concat(Instant.now().toString()));
//        System.out.println("sent message: " + message.getId());
        MailSenderV2 mailSenderV2 = new MailSenderV2();
//        mailSenderV2.sendMail("Konkord720@gmail.com");
    }
}
