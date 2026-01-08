package com.anthive.article.adapter.webapi;

import com.anthive.article.SecurityTestConfiguration;
import com.anthive.article.adapter.webapi.article.dto.ArticleResponse;
import com.anthive.article.application.member.provided.MemberRegister;
import com.anthive.article.domain.article.PublishArticleRequest;
import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.member.MemberFixture;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

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

    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:" + port);

        member = memberRegister.register(MemberFixture.createRegisterRequest());
    }

    @Test
    void create_article_success() {
        ArticleResponse response = restClient.post()
                .uri("/api/v1/articles")
                .headers(h -> h.setBasicAuth(member.getEmail().address(), member.getPasswordHash()))
                .body(new ArticleCreateRequest(
                        "hi", "content", 1L
                ))
                .retrieve()
                .body(ArticleResponse.class);

        assertThat(response).isNotNull();
    }

    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long boardId;
    }

}
