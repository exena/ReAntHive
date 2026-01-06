package com.anthive.article.application.post.provided;

import com.anthive.article.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostFinder {
    Page<Post> getUsersPosts(String username, Pageable pageable);
    Post getPost(Long postId);
}
