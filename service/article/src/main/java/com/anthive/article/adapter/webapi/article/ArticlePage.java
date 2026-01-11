package com.anthive.article.adapter.webapi.article;

import com.anthive.article.adapter.webapi.article.dto.GetArticleFormResponse;
import com.anthive.article.application.article.ArticleService;
import com.anthive.article.domain.article.Article;
import com.anthive.article.domain.article.PublishArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/anthive")
@RequiredArgsConstructor
public class ArticlePage {

    private final ArticleService postService;

    @GetMapping("/{username}/catalog-list")
    public String list_catalog(Model model, @PageableDefault(size = 20) Pageable pageable, @PathVariable("username") String username) {
        Pageable adjusted = PageRequest.of(
             Math.max(0, pageable.getPageNumber() - 1), //유저 측에서는 1부터 시작하지만 내부적으론 0부터 시작함.
             pageable.getPageSize(),
             pageable.getSort()
        );
        Page<Article> posts = postService.getAuthorsArticles(username, adjusted);
        // Page<Article> posts = postService.readAllPage(1L, (long) pageable.getPageNumber() + 1, (long) pageable.getPageSize());
        int startPage = Math.max(1, posts.getPageable().getPageNumber() / 5 * 5 + 1);
        int endPage = Math.min(posts.getTotalPages(), startPage + 4);
        if(endPage == 0)
            endPage = 1;
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("posts", posts);
        // model.addAttribute("blogger", username); // ${#authentication.name}으로 대체
        return "anthive/list_catalog";
    }

    @GetMapping(value = "/form", params = {})
    public String form(Model model) {
        model.addAttribute("postForm", new GetArticleFormResponse());
        return "anthive/form";
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping(value = "/form", params = {"postId"})
    public String form(Model model, @RequestParam("postId") Long postId, Authentication auth) {
        try {
            postService.checkAuthorPermission(postId, auth.getName());
            model.addAttribute("postForm", GetArticleFormResponse.of(postService.getArticle(postId)));
        } catch (Exception e){
            return "redirect:/anthive/form";
        }
        return "anthive/form";
    }

    @PostMapping("/form")
    public String postForm(@Validated PublishArticleRequest request, BindingResult bindingResult, Authentication auth) {
        if(bindingResult.hasErrors()){
            return "anthive/form";
        }
        //새로 만들때
        if(request.getArticleId() == null) {
            postService.publishArticle(auth.getName(),request);
        }
        //수정할때
        else{
            try {
                postService.checkAuthorPermission(request.getArticleId(), auth.getName());
                postService.publishArticle(auth.getName(), request);
            }catch (Exception e){
                return "anthive/form";
            }
        }
        return "redirect:/anthive/"+ auth.getName() +"/catalog-list";
    }

    @GetMapping(value = "/{username}/post/{postId}")
    public String view(Model model, @PathVariable("postId") Long postId){
        // 실제로 어떤 username이 넘어오든 관계가 없다는 문제가 있음.
        Article article = postService.getArticle(postId);
        model.addAttribute("postForm", article);
        return "anthive/view";
    }

}