package com.anthive.article.application.article.provided;

import com.anthive.article.domain.article.Article;
import com.anthive.article.domain.article.PublishArticleRequest;
import jakarta.validation.Valid;

public interface ArticleModify {
    Article publishArticle(String username, @Valid PublishArticleRequest request);
    void editArticle(String username, @Valid PublishArticleRequest request);
    void deleteArticle(Long postId, String username);
}
