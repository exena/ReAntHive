package com.exena.anthive.application.member.provided;

import com.exena.anthive.domain.member.Member;

public interface MemberFinder {
    Member find(Long memberId);
}
