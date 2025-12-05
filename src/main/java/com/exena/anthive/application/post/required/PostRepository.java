package com.exena.anthive.application.post.required;

import com.exena.anthive.domain.member.Member;
import com.exena.anthive.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByMember(Member member, Pageable pageable);
}