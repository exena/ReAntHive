package com.anthive.article.application.article.provided;

import com.anthive.article.domain.article.Article;
import com.anthive.article.domain.article.PublishArticleFormRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface ArticleModify {
    Article publishPost(String username, @Valid PublishArticleFormRequest request);
    void editPost(String username, @Valid PublishArticleFormRequest request);
    void deletePost(Long postId, Authentication auth);
}
