package com.anthive.article.domain.article;

public class PostFixture {

    public static PublishArticleFormRequest getPublishBlogpostFormRequest() {
        return new PublishArticleFormRequest(null, "Hello World", "This is content");
    }
}
