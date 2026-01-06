package com.anthive.article.domain.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishBlogpostFormRequest {
    private Long postId;
    @NotBlank
    @Size(min=1, max=50)
    private String title;
    private String content;
}
