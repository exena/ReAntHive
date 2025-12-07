package com.exena.anthive.application.post.required;

import com.exena.anthive.application.member.required.MemberRepository;
import com.exena.anthive.domain.member.Member;
import com.exena.anthive.domain.post.Post;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static com.exena.anthive.domain.member.MemberFixture.createPasswordEncoder;
import static com.exena.anthive.domain.member.MemberFixture.createRegisterRequest;
import static com.exena.anthive.domain.post.PostFixture.getPublishBlogpostFormRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("findByMember()가 해당 member의 게시글 목록을 페이징하여 반환한다")
    void findByMemberTest() {

        // given
        Member member = Member.register(createRegisterRequest(), createPasswordEncoder());
        memberRepository.save(member);

        Post p1 = Post.of(getPublishBlogpostFormRequest(), member);
        Post p2 = Post.of(getPublishBlogpostFormRequest(), member);
        Post p3 = Post.of(getPublishBlogpostFormRequest(), member);
        postRepository.save(p1);
        postRepository.save(p2);
        postRepository.save(p3);

        entityManager.flush();
        entityManager.clear();

        // when
        Page<Post> page = postRepository.findByMember(member, PageRequest.of(0, 2));

        // then
        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getContent().size()).isEqualTo(2);
//        assertThat(page.getContent().getFirst().getMember()).isEqualTo(member); 프록시 객체 equals 정의하기
    }
}