package com.exena.anthive.application.post.provided;

import com.exena.anthive.application.post.PostService;
import com.exena.anthive.domain.member.Member;
import com.exena.anthive.domain.post.Post;
import com.exena.anthive.domain.shared.Email;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostPermissionSpyTest {

    @InjectMocks
    @Spy
    private PostService postService;   // <— Spy로 만들어야 내부 메서드 mocking 가능

    @Test
    void mock_internal_getPost() {
        // given
        Post mockPost = Mockito.mock(Post.class);
        Member mockMember = Mockito.mock(Member.class);
        when(mockMember.getEmail()).thenReturn(new Email("author@test.com"));
        when(mockPost.getMember()).thenReturn(mockMember);

        // ★ 핵심: getPost 내부 호출을 stub
        doReturn(mockPost).when(postService).getPost(1L);

        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getName()).thenReturn("author@test.com");

        // when & then
        assertDoesNotThrow(() ->
                postService.checkAuthorPermission(1L, auth)
        );
    }
}

