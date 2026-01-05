package com.exena.anthive.adapter.webapi.post;

import com.exena.anthive.application.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostApi {

    private final PostService postService;

    @DeleteMapping("/post/{id}")
    void deletePost(@PathVariable("id") Long id, Authentication authentication){
        postService.deletePost(id, authentication);
    }
}