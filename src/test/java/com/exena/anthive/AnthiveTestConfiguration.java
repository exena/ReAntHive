package com.exena.anthive;

import com.exena.anthive.application.provided.EmailSender;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class AnthiveTestConfiguration {

    @Bean
    public EmailSender emailSender(){
        return (email, subject, body) -> System.out.println("Sending Email" + email);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return MemberFixture.createPasswordEncoder();
//    }
}