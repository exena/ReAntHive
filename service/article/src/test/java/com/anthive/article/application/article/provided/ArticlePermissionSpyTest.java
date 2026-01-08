package com.anthive.article.application.article.provided;

import com.anthive.article.application.article.ArticleService;
import com.anthive.article.domain.article.Article;
import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.shared.Email;
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
class ArticlePermissionSpyTest {

    @InjectMocks
    @Spy
    private ArticleService postService;   // <— Spy로 만들어야 내부 메서드 mocking 가능

    @Test
    void mock_internal_getPost() {
        // given
        Article mockArticle = Mockito.mock(Article.class);
        Member mockMember = Mockito.mock(Member.class);
        when(mockMember.getEmail()).thenReturn(new Email("author@test.com"));
        when(mockArticle.getMember()).thenReturn(mockMember);

        // ★ 핵심: getPost 내부 호출을 stub
        doReturn(mockArticle).when(postService).getArticle(1L);

        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getName()).thenReturn("author@test.com");

        // when & then
        assertDoesNotThrow(() ->
                postService.checkAuthorPermission(1L, auth.getName())
        );
    }
}

