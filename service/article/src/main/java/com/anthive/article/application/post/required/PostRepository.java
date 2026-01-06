package com.anthive.article.application.post.required;

import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByMember(Member member, Pageable pageable);
}