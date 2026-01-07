package com.anthive.article.adapter.webapi.article.dto;

import com.anthive.article.domain.article.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetArticleFormResponse {
    private Long id;
    private String title;
    private String content;

    public static GetArticleFormResponse of(Article article){
        return new GetArticleFormResponse(article.getId(), article.getTitle(), article.getContent());
    }
}