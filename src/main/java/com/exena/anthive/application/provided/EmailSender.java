package com.exena.anthive.application.provided;

import com.exena.anthive.domain.Email;

public interface EmailSender {
    void send(Email email, String subject, String body);
}
