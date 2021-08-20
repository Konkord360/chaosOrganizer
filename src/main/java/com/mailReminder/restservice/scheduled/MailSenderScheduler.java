package com.mailReminder.restservice.scheduled;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;

@Configuration
@EnableScheduling
public class MailSenderScheduler {

    @Scheduled(fixedDelay = 60000)
    public void test(){
        System.out.println("regularly printed message ".concat(Instant.now().toString()));
    }
}
