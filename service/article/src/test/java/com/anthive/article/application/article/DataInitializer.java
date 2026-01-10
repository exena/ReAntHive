package com.anthive.article.application.article;

import com.anthive.article.SecurityTestConfiguration;
import com.anthive.article.application.member.provided.MemberRegister;
import com.anthive.article.domain.article.Article;
import com.anthive.article.domain.article.PostFixture;
import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.member.MemberFixture;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import kuke.board.common.snowflake.Snowflake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Import(SecurityTestConfiguration.class)
@ActiveProfiles("test")
public class DataInitializer {
    @Autowired
    EntityManager entityManager;
    @Autowired
    TransactionTemplate transactionTemplate;
    Snowflake snowflake = new Snowflake();
    CountDownLatch latch = new CountDownLatch(EXECUTE_COUNT);

    @Autowired
    MemberRegister memberRegister;
    Member member;

    static final int BULK_INSERT_SIZE = 10;
    static final int EXECUTE_COUNT = 10;

    @BeforeEach
    void setUp() {
        member = memberRegister.register(MemberFixture.createRegisterRequest());
        // entityManager.flush(); No EntityManager with actual transaction available for current thread 에러 발생
        // entityManager.clear();
    }


    @Test
    void initialize() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 0; i < EXECUTE_COUNT; i++) {
            executorService.submit(() -> {
                insert();
                latch.countDown();
                System.out.println("latch.getCount() = " + latch.getCount());
            });
        }
        latch.await();
        executorService.shutdown();
    }

    void insert() {
        transactionTemplate.executeWithoutResult(status -> {
            for(int i = 0; i < BULK_INSERT_SIZE; i++) {
                Article article = Article.of(snowflake.nextId(), PostFixture.getPublishBlogpostFormRequest(), member);
                entityManager.persist(article);
            }
        });
    }
}
