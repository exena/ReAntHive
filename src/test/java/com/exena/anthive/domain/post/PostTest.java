package com.exena.anthive.domain.post;

import com.exena.anthive.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.exena.anthive.domain.post.PostFixture.getPublishBlogpostFormRequest;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PostTest {
    Member member;
    Post post;

    @BeforeEach
    void setUp() {
        // given
        PublishBlogpostFormRequest request = getPublishBlogpostFormRequest();

        // when
        member = Mockito.mock(Member.class);
        post = Post.of(request, member);
    }

    @Test
    @DisplayName("Post.of(): 요청값과 Member로 올바른 Post가 생성된다")
    void createPostFromFactoryMethod() {
        // then
        assertThat(post.getId()).isNull();
        assertThat(post.getTitle()).isEqualTo("Hello World");
        assertThat(post.getContent()).isEqualTo("This is content");
        assertThat(post.getMember()).isEqualTo(member);
    }

    @Test
    @DisplayName("title이 null이면 예외를 반환한다")
    void constructorNullCheck() {
        //when
        var publishBlogpostFormRequest = new PublishBlogpostFormRequest();

        //then
        assertThatThrownBy(()->Post.of(publishBlogpostFormRequest,member))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("title과 content는 수정 가능한 필드이다")
    void changePostFields() {
        // when
        post.changeTitle("new title");
        post.changeContent("new content");

        // then
        assertThat(post.getTitle()).isEqualTo("new title");
        assertThat(post.getContent()).isEqualTo("new content");
    }

    @Test
    @DisplayName("title 수정시에도 Null이 들어가선 안된다.")
    void changePostFieldsNullCheck() {
        // then
        assertThatThrownBy(()->post.changeTitle(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Post 생성 시 Member가 정상적으로 매핑되는지 테스트")
    void memberMappingTest() {
        // then
        assertThat(post.getMember()).isNotNull();
        assertThat(post.getMember()).isEqualTo(member);
    }

}