package com.anthive.article.application.article.required;

import com.anthive.article.application.member.required.MemberRepository;
import com.anthive.article.domain.article.Article;
import com.anthive.article.domain.member.Member;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.anthive.article.domain.member.MemberFixture.createPasswordEncoder;
import static com.anthive.article.domain.member.MemberFixture.createRegisterRequest;
import static com.anthive.article.domain.article.PostFixture.getPublishBlogpostFormRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

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

        Article p1 = Article.of(1L, getPublishBlogpostFormRequest(), member);
        Article p2 = Article.of(2L, getPublishBlogpostFormRequest(), member);
        Article p3 = Article.of(3L, getPublishBlogpostFormRequest(), member);
        articleRepository.save(p1);
        articleRepository.save(p2);
        articleRepository.save(p3);

        entityManager.flush();
        entityManager.clear();

        // when
        Page<Article> page = articleRepository.findByMember(member, PageRequest.of(0, 2));

        // then
        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getContent().size()).isEqualTo(2);
//        assertThat(page.getContent().getFirst().getMember()).isEqualTo(member); 프록시 객체 equals 정의하기
    }
}