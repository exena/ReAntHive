package com.anthive.article.adapter.webapi;

import com.anthive.article.SecurityTestConfiguration;
import com.anthive.article.application.article.ArticlePageResponse;
import com.anthive.article.application.article.ArticleResponse;
import com.anthive.article.application.member.provided.MemberFinder;
import com.anthive.article.application.member.provided.MemberRegister;
import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.member.MemberFixture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(SecurityTestConfiguration.class)
@ActiveProfiles("test")
class ArticleApiTest {

    @LocalServerPort
    int port;

    RestClient restClient;
    Member member;
    @Autowired
    MemberRegister memberRegister;
    @Autowired
    MemberFinder memberFinder;

    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:" + port);

        // member = memberRegister.register(MemberFixture.createRegisterRequest());
        member = memberFinder.find(1L);
    }

    @Test
    void create_article_success() {
        ArticleResponse response = create(new ArticleCreateRequest("hi", "content", 1L));
        assertThat(response).isNotNull();
    }

    ArticleResponse create(ArticleCreateRequest articleCreateRequest) {
        return restClient.post()
                .uri("/api/v1/articles")
                .headers(h -> h.setBasicAuth(member.getEmail().address(), member.getPasswordHash()))
                .body(articleCreateRequest)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void read_article_success() {
        ArticleResponse postResponse = create(new ArticleCreateRequest("hi", "content", 1L));

        ArticleResponse getResponse = read(postResponse.id());

        assertThat(getResponse.id()).isEqualTo(postResponse.id());
    }

    ArticleResponse read(Long articleId) {
        return restClient.get()
                .uri("/api/v1/articles/{articleId}", articleId)
                .headers(h -> h.setBasicAuth(member.getEmail().address(), member.getPasswordHash()))
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void update_article_success() {
        ArticleResponse postResponse = create(new ArticleCreateRequest("hi", "content", 1L));

        ArticleResponse updateResponse = restClient.put()
                .uri("/api/v1/articles/{articleId}", postResponse.id())
                .headers(h -> h.setBasicAuth(member.getEmail().address(), member.getPasswordHash()))
                .body(new ArticleUpdateRequest(postResponse.id(),"hello", "content", 1L))
                .retrieve()
                .body(ArticleResponse.class);

        ArticleResponse getResponse = read(postResponse.id());
        assertThat(getResponse.title()).isEqualTo("hello");
    }

    @Test
    void delete_article_success() {
        ArticleResponse postResponse = create(new ArticleCreateRequest("hi", "content", 1L));

        restClient.delete()
                .uri("/api/post/{articleId}", postResponse.id())
                .headers(h -> h.setBasicAuth(member.getEmail().address(), member.getPasswordHash()))
                .retrieve()
                .body(ArticleResponse.class);

        assertThatThrownBy(() -> read(postResponse.id()))
                .isInstanceOf(RestClientResponseException.class)
                .hasMessageContaining("404");
    }

    @Test
    void readAllTest() {
        ArticlePageResponse response = restClient.get()
                .uri("/api/v1/articles?boardId=1&pageSize=30&page=50000")
                .headers(h -> h.setBasicAuth(member.getEmail().address(), member.getPasswordHash()))
                .retrieve()
                .body(ArticlePageResponse.class);

        System.out.println("response.getArticleCount() = " + response.getArticleCount());
        for (ArticleResponse article : response.getArticles()) {
            System.out.println("articleId = " + article.id());
        }
    }

    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private Long articleId;
        private String title;
        private String content;
        private Long boardId;
    }

}
