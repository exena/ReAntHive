package com.anthive.article.adapter.integration;

import com.anthive.article.application.member.provided.EmailSender;
import com.anthive.article.domain.shared.Email;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

@Component
@Fallback
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.printf("[DummyEmailSender] Email sending skipped. to=%s, subject=%s%n",
                email.address(), subject);
    }
}
