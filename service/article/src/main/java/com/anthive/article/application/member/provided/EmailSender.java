package com.anthive.article.application.member.provided;

import com.anthive.article.domain.shared.Email;

public interface EmailSender {
    void send(Email email, String subject, String body);
}
