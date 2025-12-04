package com.exena.anthive.application.provided;

import com.exena.anthive.domain.Member;
import com.exena.anthive.domain.MemberRegisterRequest;
import jakarta.validation.Valid;

public interface MemberRegister {
    Member register(@Valid MemberRegisterRequest registerRequest);
}
