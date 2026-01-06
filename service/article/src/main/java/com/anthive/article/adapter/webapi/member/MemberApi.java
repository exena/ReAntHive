package com.anthive.article.adapter.webapi.member;

import com.anthive.article.adapter.webapi.member.dto.MemberRegisterResponse;
import com.anthive.article.application.member.provided.MemberRegister;
import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApi {
    private final MemberRegister memberRegister;

    @PostMapping("/api/members")
    public MemberRegisterResponse register(@RequestBody @Valid MemberRegisterRequest memberRegisterRequest){
        Member member = memberRegister.register(memberRegisterRequest);

        return MemberRegisterResponse.of(member);
    }

}
