package com.exena.anthive.application.member.provided;

import com.exena.anthive.domain.shared.Email;

public interface EmailSender {
    void send(Email email, String subject, String body);
}
