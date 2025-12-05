package com.exena.anthive.application.member.required;

import com.exena.anthive.domain.shared.Email;
import com.exena.anthive.domain.member.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);
    Optional<Member> findByEmail(Email email);
    Optional<Member> findById(Long memberId);
}
