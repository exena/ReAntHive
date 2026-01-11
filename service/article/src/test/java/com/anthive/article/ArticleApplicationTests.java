package com.anthive.article;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArticleApplicationTests {

    @Test
    void run() {
        try (MockedStatic<SpringApplication> mockedStatic = Mockito.mockStatic(SpringApplication.class)) {

            ArticleApplication.main(new String[0]);

            mockedStatic.verify(() ->
                    SpringApplication.run(ArticleApplication.class, new String[]{})
            );
        }
    }

}
