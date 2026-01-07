package com.anthive.article.domain.article;

import com.anthive.article.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor(access= AccessLevel.PROTECTED)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    @JsonIgnore
    private Member member;

    public static Article of(PublishArticleFormRequest request, Member member){
        String title = requireNonNull(request.getTitle());
        String content = request.getContent();
        return new Article(null, title, content, requireNonNull(member));
    }

    public void changeTitle(String newTitle){
        this.title = requireNonNull(newTitle);
    }

    public void changeContent(String newContent){
        this.content = newContent;
    }
}