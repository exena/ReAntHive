package com.anthive.article.adapter.webapi.post.dto;

import com.anthive.article.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBlogpostFormResponse {
    private Long id;
    private String title;
    private String content;

    public static GetBlogpostFormResponse of(Post post){
        return new GetBlogpostFormResponse(post.getId(), post.getTitle(), post.getContent());
    }
}