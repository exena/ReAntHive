package com.anthive.article.application.article.provided;

import com.anthive.article.domain.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ArticleFinder {
    Page<Article> getUsersPosts(String username, Pageable pageable);
    Article getPost(Long postId);
}
