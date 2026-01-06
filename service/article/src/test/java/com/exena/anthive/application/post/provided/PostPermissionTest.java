package com.exena.anthive.application.post.provided;

import com.exena.anthive.application.member.required.MemberRepository;
import com.exena.anthive.application.post.required.PostRepository;
import com.exena.anthive.domain.member.Member;
import com.exena.anthive.domain.post.Post;
import com.exena.anthive.domain.shared.Email;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PostPermissionTest {

    @MockitoBean
    private MemberRepository memberRepository; // 실제 객체 사용 X

    @MockitoBean
    private PostRepository postRepository; // 실제 객체 사용 X

    @Autowired
    private PostPermission postPermission; // 구현체는 PostService

    @Test
    void author_can_delete_post() {
        // given
        Long postId = 1L;

        Member mockMember = Mockito.mock(Member.class);
        Mockito.when(mockMember.getEmail()).thenReturn(new Email("user@test.com"));

        Post mockPost = Mockito.mock(Post.class);
        Mockito.when(mockPost.getMember()).thenReturn(mockMember);

        // PostRepository 동작 모킹
        Mockito.when(postRepository.findById(postId))
                .thenReturn(Optional.of(mockPost));

        // 인증 객체 모킹
        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(auth.getName()).thenReturn("user@test.com");

        // when & then — 예외 없어야 정상
        assertDoesNotThrow(() ->
                postPermission.checkAuthorPermission(postId, auth)
        );
    }

    @Test
    void non_author_cannot_delete_post() {
        // given
        Long postId = 1L;

        Member mockAuthor = Mockito.mock(Member.class);
        Mockito.when(mockAuthor.getEmail()).thenReturn(new Email("author@test.com"));

        Post mockPost = Mockito.mock(Post.class);
        Mockito.when(mockPost.getMember()).thenReturn(mockAuthor);

        // PostRepository 동작 모킹
        Mockito.when(postRepository.findById(postId))
                .thenReturn(Optional.of(mockPost));

        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(auth.getName()).thenReturn("other@test.com");

        // when & then — SecurityException 발생해야 함
        assertThrows(SecurityException.class, () ->
                postPermission.checkAuthorPermission(postId, auth)
        );
    }
}

