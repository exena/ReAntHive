package com.anthive.article.application.article.provided;

import com.anthive.article.AnthiveTestConfiguration;
import com.anthive.article.application.member.provided.MemberRegister;
import com.anthive.article.domain.article.Article;
import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.member.MemberFixture;
import com.anthive.article.domain.article.ArticleNotFoundException;
import com.anthive.article.domain.article.PublishArticleFormRequest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;

import static com.anthive.article.domain.article.PostFixture.getPublishBlogpostFormRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@Transactional
@Import(AnthiveTestConfiguration.class)
class ArticleModifyTest {
    @Autowired MemberRegister memberRegister;
    @Autowired
    ArticleModify articleModify;
    @Autowired
    ArticleFinder articleFinder;
    @Autowired EntityManager entityManager;
    Member member;
    Article article;

    @BeforeEach
    void setUp() {
        member = memberRegister.register(MemberFixture.createRegisterRequest());
        article = articleModify.publishPost(member.getEmail().address(), getPublishBlogpostFormRequest());
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void publishPost() {
        assertThat(article.getId()).isNotNull();
    }

    @Test
    void publishPostFail() {
        PublishArticleFormRequest request = new PublishArticleFormRequest(null, null, "This is content");

        assertThatThrownBy(()-> articleModify.publishPost(member.getEmail().address(), request)).
                isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void editPost() {
        articleModify.editPost(member.getEmail().address(),  new PublishArticleFormRequest(article.getId(), "new title", "new content"));
        entityManager.flush();
        entityManager.clear();

        assertThat(articleFinder.getPost(article.getId()).getContent()).isEqualTo("new content");
    }

    @Test
    void editPostFail() {
        Article article = articleModify.publishPost(member.getEmail().address(), getPublishBlogpostFormRequest());

        assertThatThrownBy(()-> articleModify.editPost(member.getEmail().address(),  new PublishArticleFormRequest(article.getId(), null, "new content")))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void deletePost() {
        Authentication auth = mock(Authentication.class);
        given(auth.getName()).willReturn(member.getEmail().address());
        articleModify.deletePost(article.getId(), auth);
        entityManager.flush();
        entityManager.clear();

        assertThatThrownBy(()-> articleFinder.getPost(article.getId()))
                .isInstanceOf(ArticleNotFoundException.class);
    }
}