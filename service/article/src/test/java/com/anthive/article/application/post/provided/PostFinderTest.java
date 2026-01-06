package com.anthive.article.application.post.provided;

import com.anthive.article.AnthiveTestConfiguration;
import com.anthive.article.application.member.provided.MemberRegister;
import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.member.MemberFixture;
import com.anthive.article.domain.post.Post;
import com.anthive.article.domain.post.PostNotFoundException;
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

import static com.anthive.article.domain.post.PostFixture.getPublishBlogpostFormRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Import(AnthiveTestConfiguration.class)
class PostFinderTest {
    @Autowired MemberRegister memberRegister;
    @Autowired
    PostModify postModify;
    @Autowired
    PostFinder postFinder;
    @Autowired EntityManager entityManager;
    Member member;
    Post post;

    @BeforeEach
    void setUp() {
        member = memberRegister.register(MemberFixture.createRegisterRequest());
        post = postModify.publishPost(member.getEmail().address(), getPublishBlogpostFormRequest());

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void getUsersPosts_success() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Post> result = postFinder.getUsersPosts(member.getEmail().address(), pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    void getUsersPosts_memberNotFound() {
        // given
        String username = "unknown@test.com";
        Pageable pageable = PageRequest.of(0, 10);

        // when - then
        assertThrows(UsernameNotFoundException.class,
                () -> postFinder.getUsersPosts(username, pageable));
    }

    @Test
    void getPost_success() {
        // when
        Post result = postFinder.getPost(post.getId());

        // then
        assertThat(result.getId()).isEqualTo(post.getId());
    }

    @Test
    void getPost_notFound() {
        assertThrows(PostNotFoundException.class,
                () -> postFinder.getPost(1L));
    }
}