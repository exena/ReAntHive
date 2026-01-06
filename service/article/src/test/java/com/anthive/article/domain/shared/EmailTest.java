package com.anthive.article.domain.shared;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {
    @Test
    void equality() {
        var email1 = new Email("test01@test.com");
        var email2 = new Email("test01@test.com");
        assertThat(email1).isEqualTo(email2);
    }
}