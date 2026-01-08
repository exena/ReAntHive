package com.anthive.article.domain.article;

import com.anthive.article.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor(access= AccessLevel.PROTECTED)
public class Article {
    @Id
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    @JsonIgnore
    private Member member;

    private Long boardId;

    private Instant publishDate;

    public static Article of(Long id, PublishArticleRequest request, Member member){
        Long articleId = requireNonNull(id);
        String title = requireNonNull(request.getTitle());
        Member author = requireNonNull(member);
        String content = request.getContent();
        Long boardId = request.getBoardId();
        Instant publishDate = Instant.now();
        return new Article(articleId, title, content, author, boardId, publishDate);
    }

    public void changeTitle(String newTitle){
        this.title = requireNonNull(newTitle);
    }

    public void changeContent(String newContent){
        this.content = newContent;
    }
}