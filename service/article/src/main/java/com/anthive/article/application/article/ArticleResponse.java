package com.anthive.article.application.article;

import com.anthive.article.domain.article.Article;
import com.anthive.article.domain.member.Member;

import java.time.Instant;
import java.time.LocalDateTime;

public record ArticleResponse(Long id, String title, String content, String authorEmailAddress, Long boardId, Instant publishDate) {
    public static ArticleResponse from(Article article){
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getMember().getEmail().address(),
                article.getBoardId(),
                article.getPublishDate()
        );
    }
}
