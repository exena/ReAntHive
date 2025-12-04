package com.exena.anthive.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

public class MemberFixture {

    public static MemberRegisterRequest createRegisterRequest() {
        return new MemberRegisterRequest("test01@test.com", "테스트닉", "my_password", new HashSet<Role>());
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
