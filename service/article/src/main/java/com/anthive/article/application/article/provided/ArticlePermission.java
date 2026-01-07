package com.anthive.article.application.article.provided;

import org.springframework.security.core.Authentication;

public interface ArticlePermission {
    void checkAuthorPermission(Long postId, Authentication auth);
}
