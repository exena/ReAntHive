package com.exena.anthive.application.member.provided;

import com.exena.anthive.domain.member.Member;
import com.exena.anthive.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;

public interface MemberRegister {
    Member register(@Valid MemberRegisterRequest registerRequest);
}
