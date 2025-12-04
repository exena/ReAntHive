package com.exena.anthive.application;

import com.exena.anthive.application.provided.MemberFinder;
import com.exena.anthive.application.required.MemberRepository;
import com.exena.anthive.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {
    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(()-> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + memberId));
    }
}
