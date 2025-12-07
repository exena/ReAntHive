package com.exena.anthive.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

import static com.exena.anthive.domain.member.MemberFixture.createMember;
import static com.exena.anthive.domain.member.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = createPasswordEncoder();
        member = createMember();
    }

    @Test
    void registerMember() {
        assertThat(member.getNickname()).isNotNull();
    }

    @Test
    void constructorNullCheck() {
        var registerRequest = new MemberRegisterRequest("test01@test.com", null, null, null);
        assertThatThrownBy(()->Member.register(registerRequest, passwordEncoder))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("my_password", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("wrong_password", passwordEncoder)).isFalse();
    }

    @Test
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("테스트닉");
        member.changeNickname("newNickname");
        assertThat(member.getNickname()).isEqualTo("newNickname");
    }

    @Test
    void changePassword() {
        member.changePassword("newPassword", passwordEncoder);
        assertThat(member.verifyPassword("newPassword", passwordEncoder)).isTrue();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(()->{
            var registerRequest = new MemberRegisterRequest("illegalEmail", "테스트닉", "my_password", new HashSet<Role>());
            Member.register(registerRequest, passwordEncoder);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}