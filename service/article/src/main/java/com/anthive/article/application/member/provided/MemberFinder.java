package com.anthive.article.application.member.provided;

import com.anthive.article.domain.member.Member;

public interface MemberFinder {
    Member find(Long memberId);
}
