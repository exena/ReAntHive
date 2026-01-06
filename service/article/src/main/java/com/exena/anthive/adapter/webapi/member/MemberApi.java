package com.exena.anthive.adapter.webapi.member;

import com.exena.anthive.adapter.webapi.member.dto.MemberRegisterResponse;
import com.exena.anthive.application.member.provided.MemberRegister;
import com.exena.anthive.domain.member.Member;
import com.exena.anthive.domain.member.MemberRegisterRequest;
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
