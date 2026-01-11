package com.anthive.article.adapter.webapi.article;

import com.anthive.article.application.article.ArticlePageResponse;
import com.anthive.article.application.article.ArticleResponse;
import com.anthive.article.application.article.ArticleService;
import com.anthive.article.domain.article.Article;
import com.anthive.article.domain.article.PublishArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleApi {

    private final ArticleService articleService;

    @GetMapping("/v1/articles/{articleId}")
    public ArticleResponse read(@PathVariable("articleId") Long articleId){
        Article article = articleService.getArticle(articleId);
        return ArticleResponse.from(article);
    }

    @GetMapping("/v1/articles")
    public ArticlePageResponse readAll(
            @RequestParam("boardId") Long boardId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ) {
        return articleService.readAll(boardId, page, pageSize);
    }

    @PostMapping("/v1/articles")
    public ArticleResponse create(@RequestBody PublishArticleRequest request, Authentication auth){
        Article article = articleService.publishArticle(auth.getName(),request);
        return ArticleResponse.from(article);
    }

    @PutMapping("v1/articles/{articleId}")
    public ArticleResponse update(@PathVariable("articleId") Long articleId, @RequestBody PublishArticleRequest request, Authentication auth){
        articleService.checkAuthorPermission(articleId, auth.getName());
        articleService.editArticle(auth.getName(), request);
        return ArticleResponse.from(articleService.getArticle(articleId));
    }

    @DeleteMapping("/post/{articleId}")
    public void delete(@PathVariable("articleId") Long articleId, Authentication auth){
        articleService.checkAuthorPermission(articleId, auth.getName());
        articleService.deleteArticle(articleId, auth.getName());
    }
}