package com.exena.anthive.application.post.provided;

import com.exena.anthive.AnthiveTestConfiguration;
import com.exena.anthive.application.member.provided.MemberRegister;
import com.exena.anthive.domain.member.Member;
import com.exena.anthive.domain.member.MemberFixture;
import com.exena.anthive.domain.post.Post;
import com.exena.anthive.domain.post.PostNotFoundException;
import com.exena.anthive.domain.shared.Email;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static com.exena.anthive.domain.post.PostFixture.getPublishBlogpostFormRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@Import(AnthiveTestConfiguration.class)
class PostFinderTest {
    @Autowired MemberRegister memberRegister;
    @Autowired PostModify postModify;
    @Autowired PostFinder postFinder;
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