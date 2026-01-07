package com.anthive.article.domain.article;

import com.anthive.article.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.anthive.article.domain.article.PostFixture.getPublishBlogpostFormRequest;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ArticleTest {
    Member member;
    Article article;

    @BeforeEach
    void setUp() {
        // given
        PublishArticleFormRequest request = getPublishBlogpostFormRequest();

        // when
        member = Mockito.mock(Member.class);
        article = Article.of(request, member);
    }

    @Test
    @DisplayName("Post.of(): 요청값과 Member로 올바른 Post가 생성된다")
    void createPostFromFactoryMethod() {
        // then
        assertThat(article.getId()).isNull();
        assertThat(article.getTitle()).isEqualTo("Hello World");
        assertThat(article.getContent()).isEqualTo("This is content");
        assertThat(article.getMember()).isEqualTo(member);
    }

    @Test
    @DisplayName("title이 null이면 예외를 반환한다")
    void constructorNullCheck() {
        //when
        var publishBlogpostFormRequest = new PublishArticleFormRequest();

        //then
        assertThatThrownBy(()-> Article.of(publishBlogpostFormRequest,member))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("title과 content는 수정 가능한 필드이다")
    void changePostFields() {
        // when
        article.changeTitle("new title");
        article.changeContent("new content");

        // then
        assertThat(article.getTitle()).isEqualTo("new title");
        assertThat(article.getContent()).isEqualTo("new content");
    }

    @Test
    @DisplayName("title 수정시에도 Null이 들어가선 안된다.")
    void changePostFieldsNullCheck() {
        // then
        assertThatThrownBy(()-> article.changeTitle(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Post 생성 시 Member가 정상적으로 매핑되는지 테스트")
    void memberMappingTest() {
        // then
        assertThat(article.getMember()).isNotNull();
        assertThat(article.getMember()).isEqualTo(member);
    }

}