package com.anthive.article.application.member.provided;

import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;

public interface MemberRegister {
    Member register(@Valid MemberRegisterRequest registerRequest);
}
