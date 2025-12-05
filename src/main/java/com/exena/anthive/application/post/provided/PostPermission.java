package com.exena.anthive.application.post.provided;

import org.springframework.security.core.Authentication;

public interface PostPermission {
    void checkAuthorPermission(Long postId, Authentication auth);
}
