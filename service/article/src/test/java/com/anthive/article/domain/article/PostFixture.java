package com.anthive.article.domain.article;

public class PostFixture {

    public static PublishArticleRequest getPublishBlogpostFormRequest() {
        return new PublishArticleRequest(null, "Hello World", "This is content", 1L);
    }
}
