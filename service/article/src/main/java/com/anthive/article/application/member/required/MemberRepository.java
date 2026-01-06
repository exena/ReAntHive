package com.anthive.article.application.member.required;

import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.shared.Email;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);
    Optional<Member> findByEmail(Email email);
    Optional<Member> findById(Long memberId);
}
