package com.exena.anthive.domain.member;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;

public class MemberFixture {

    public static MemberRegisterRequest createRegisterRequest() {
        return new MemberRegisterRequest("test01@test.com", "테스트닉", "my_password", new HashSet<Role>());
    }

    public static MemberRegisterRequest createRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "테스트닉", "my_password", new HashSet<Role>());
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
