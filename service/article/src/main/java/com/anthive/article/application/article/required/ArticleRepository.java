package com.anthive.article.application.article.required;

import com.anthive.article.domain.article.Article;
import com.anthive.article.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByMember(Member member, Pageable pageable);
}