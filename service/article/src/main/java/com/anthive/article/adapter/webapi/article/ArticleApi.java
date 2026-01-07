package com.anthive.article.adapter.webapi.article;

import com.anthive.article.application.article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleApi {

    private final ArticleService postService;

    @DeleteMapping("/post/{id}")
    void deletePost(@PathVariable("id") Long id, Authentication authentication){
        postService.deletePost(id, authentication);
    }
}