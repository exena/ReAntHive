package com.exena.anthive.adapter.webapi;

import com.exena.anthive.adapter.webapi.dto.MemberRegisterResponse;
import com.exena.anthive.application.provided.MemberRegister;
import com.exena.anthive.domain.Member;
import com.exena.anthive.domain.MemberRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApi {
    private final MemberRegister memberRegister;

    @PostMapping("/members")
    public MemberRegisterResponse register(@RequestBody MemberRegisterRequest memberRegisterRequest){
        Member member = memberRegister.register(memberRegisterRequest);

        return MemberRegisterResponse.of(member);
    }

}
