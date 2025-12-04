package com.exena.anthive.application.provided;

import com.exena.anthive.domain.Member;

public interface MemberFinder {
    Member find(Long memberId);
}
