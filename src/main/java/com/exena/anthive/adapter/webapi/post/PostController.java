package com.exena.anthive.adapter.webapi.post;

import com.exena.anthive.adapter.webapi.post.dto.GetBlogpostFormResponse;
import com.exena.anthive.application.post.PostService;
import com.exena.anthive.domain.post.Post;
import com.exena.anthive.domain.post.PublishBlogpostFormRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
public class PostController {

    private final PostService postService;

    @GetMapping("/{username}/catalog-list")
    public String list_catalog(Model model, @PageableDefault(size = 20) Pageable pageable, @PathVariable("username") String username) {
        Page<Post> posts = postService.getUsersPosts(username, pageable);
        int startPage = Math.max(1, posts.getPageable().getPageNumber() / 5 * 5 + 1);
        int endPage = Math.min(posts.getTotalPages(), startPage + 4);
        if(endPage == 0)
            endPage = 1;
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("posts", posts);
        model.addAttribute("blogger", username);
        return "anthive/list_catalog";
    }

    @GetMapping(value = "/form", params = {})
    public String form(Model model) {
        model.addAttribute("postForm", new GetBlogpostFormResponse());
        return "anthive/form";
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping(value = "/form", params = {"postId"})
    public String form(Model model, @RequestParam("postId") Long postId, Authentication auth) {
        try {
            postService.checkAuthorPermission(postId, auth);
            model.addAttribute("postForm", GetBlogpostFormResponse.of(postService.getPost(postId)));
        } catch (Exception e){
            return "redirect:/anthive/form";
        }
        return "anthive/form";
    }

    @PostMapping("/form")
    public String postForm(@Validated PublishBlogpostFormRequest request, BindingResult bindingResult, Authentication auth) {
        if(bindingResult.hasErrors()){
            return "anthive/form";
        }
        //새로 만들때
        if(request.getPostId() == null) {
            postService.publishPost(auth.getName(),request);
        }
        //수정할때
        else{
            try {
                postService.checkAuthorPermission(request.getPostId(), auth);
                postService.publishPost(auth.getName(), request);
            }catch (Exception e){
                return "anthive/form";
            }
        }
        return "redirect:/anthive/"+ auth.getName() +"/catalog-list";
    }

    @GetMapping(value = "/{username}/post/{postId}")
    public String view(Model model, @PathVariable("postId") Long postId){
        Post post = postService.getPost(postId);
        model.addAttribute("postForm", post);
        return "anthive/view";
    }

}