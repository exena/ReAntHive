package com.exena.anthive.application.post.provided;

import com.exena.anthive.AnthiveTestConfiguration;
import com.exena.anthive.adapter.security.AccountContext;
import com.exena.anthive.application.member.provided.MemberRegister;
import com.exena.anthive.domain.member.Member;
import com.exena.anthive.domain.member.MemberFixture;
import com.exena.anthive.domain.post.Post;
import com.exena.anthive.domain.post.PostNotFoundException;
import com.exena.anthive.domain.post.PublishBlogpostFormRequest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import static com.exena.anthive.domain.post.PostFixture.getPublishBlogpostFormRequest;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@SpringBootTest
@Transactional
@Import(AnthiveTestConfiguration.class)
class PostModifyTest {
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
    void publishPost() {
        assertThat(post.getId()).isNotNull();
    }

    @Test
    void publishPostFail() {
        PublishBlogpostFormRequest request = new PublishBlogpostFormRequest(null, null, "This is content");

        assertThatThrownBy(()->postModify.publishPost(member.getEmail().address(), request)).
                isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void editPost() {
        postModify.editPost(member.getEmail().address(),  new PublishBlogpostFormRequest(post.getId(), "new title", "new content"));
        entityManager.flush();
        entityManager.clear();

        assertThat(postFinder.getPost(post.getId()).getContent()).isEqualTo("new content");
    }

    @Test
    void editPostFail() {
        Post post = postModify.publishPost(member.getEmail().address(), getPublishBlogpostFormRequest());

        assertThatThrownBy(()->postModify.editPost(member.getEmail().address(),  new PublishBlogpostFormRequest(post.getId(), null, "new content")))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void deletePost() {
        Authentication auth = mock(Authentication.class);
        given(auth.getName()).willReturn(member.getEmail().address());
        postModify.deletePost(post.getId(), auth);
        entityManager.flush();
        entityManager.clear();

        assertThatThrownBy(()->postFinder.getPost(post.getId()))
                .isInstanceOf(PostNotFoundException.class);
    }
}