package com.anthive.article.domain.member;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;

public class MemberFixture {

    public static final String TEST_EMAIL_ADDRESS = "test01@test.com";
    public static final String TEST_PASSWORD = "my_password";

    public static MemberRegisterRequest createRegisterRequest() {
        return new MemberRegisterRequest(TEST_EMAIL_ADDRESS, "테스트닉", TEST_PASSWORD, new HashSet<Role>());
    }

    public static MemberRegisterRequest createRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "테스트닉", TEST_PASSWORD, new HashSet<Role>());
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static Member createMember() {
        return Member.register(createRegisterRequest(), createPasswordEncoder());
    }

    public static Member createMember(Long id) {
        Member member = Member.register(createRegisterRequest(), createPasswordEncoder());
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }
}
