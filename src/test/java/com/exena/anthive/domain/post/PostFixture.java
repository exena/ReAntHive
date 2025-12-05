package com.exena.anthive.domain.post;

public class PostFixture {

    public static PublishBlogpostFormRequest getPublishBlogpostFormRequest() {
        return new PublishBlogpostFormRequest(null, "Hello World", "This is content");
    }
}
