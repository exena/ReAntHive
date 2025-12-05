package com.exena.anthive.domain.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterRequest{
    @Email
    private String email;
    @Size(min = 4, max = 20)
    private String nickname;
    @Size(min = 8, max = 100)
    private String password;
    private Set<Role> roles;
}