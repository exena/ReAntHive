package com.exena.anthive.application.required;

import com.exena.anthive.domain.Email;
import com.exena.anthive.domain.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);
    Optional<Member> findByEmail(Email email);
    Optional<Member> findById(Long memberId);
}
