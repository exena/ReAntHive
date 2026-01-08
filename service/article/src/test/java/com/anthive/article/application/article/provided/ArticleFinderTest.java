package com.anthive.article.application.article.provided;

import com.anthive.article.AnthiveTestConfiguration;
import com.anthive.article.application.member.provided.MemberRegister;
import com.anthive.article.domain.article.Article;
import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.member.MemberFixture;
import com.anthive.article.domain.article.ArticleNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.anthive.article.domain.article.PostFixture.getPublishBlogpostFormRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Import(AnthiveTestConfiguration.class)
class ArticleFinderTest {
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
        article = articleModify.publishArticle(member.getEmail().address(), getPublishBlogpostFormRequest());

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void getAuthorsArticles_success() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Article> result = articleFinder.getAuthorsArticles(member.getEmail().address(), pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    void getAuthorsArticles_memberNotFound() {
        // given
        String username = "unknown@test.com";
        Pageable pageable = PageRequest.of(0, 10);

        // when - then
        assertThrows(UsernameNotFoundException.class,
                () -> articleFinder.getAuthorsArticles(username, pageable));
    }

    @Test
    void getArticle_success() {
        // when
        Article result = articleFinder.getArticle(article.getId());

        // then
        assertThat(result.getId()).isEqualTo(article.getId());
    }

    @Test
    void getArticle_notFound() {
        assertThrows(ArticleNotFoundException.class,
                () -> articleFinder.getArticle(1L));
    }
}