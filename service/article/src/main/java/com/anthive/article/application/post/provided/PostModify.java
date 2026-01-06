package com.anthive.article.application.post.provided;

import com.anthive.article.domain.post.Post;
import com.anthive.article.domain.post.PublishBlogpostFormRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface PostModify {
    Post publishPost(String username, @Valid PublishBlogpostFormRequest request);
    void editPost(String username, @Valid PublishBlogpostFormRequest request);
    void deletePost(Long postId, Authentication auth);
}
