package com.exena.anthive.adapter.integration;

import com.exena.anthive.domain.shared.Email;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DummyEmailSenderTest {

    @Test
    @StdIo
    void dummyEmailSender(StdOut stdOut) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();
        dummyEmailSender.send(new Email("test01@test.com"), "subject", "body");

        assertThat(stdOut.capturedLines()[0]).isEqualTo("[DummyEmailSender] Email sending skipped. to=test01@test.com, subject=subject");
    }
}