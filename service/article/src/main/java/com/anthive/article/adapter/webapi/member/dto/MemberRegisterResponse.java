package com.anthive.article.adapter.webapi.member.dto;

import com.anthive.article.domain.member.Member;

public record MemberRegisterResponse(Long memberId, String emailAddress) {
    public static MemberRegisterResponse of(Member member) {
        return new MemberRegisterResponse(member.getId(), member.getEmail().address());
    }
}
