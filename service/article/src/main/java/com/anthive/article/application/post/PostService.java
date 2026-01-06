package com.anthive.article.application.post;

import com.anthive.article.application.member.required.MemberRepository;
import com.anthive.article.application.post.provided.PostFinder;
import com.anthive.article.application.post.provided.PostModify;
import com.anthive.article.application.post.provided.PostPermission;
import com.anthive.article.application.post.required.PostRepository;
import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.post.Post;
import com.anthive.article.domain.post.PostNotFoundException;
import com.anthive.article.domain.post.PublishBlogpostFormRequest;
import com.anthive.article.domain.shared.Email;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class PostService implements PostFinder, PostModify, PostPermission {

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    public Page<Post> getUsersPosts(String username, Pageable pageable) {
        Member member = memberRepository.findByEmail(new Email(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return postRepository.findByMember(member, pageable);
    }


    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    public Post publishPost(String username, PublishBlogpostFormRequest request){
        Member member = memberRepository.findByEmail(new Email(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        Post post = Post.of(request, member);
        postRepository.save(post);
        return post;
    }

    public void editPost(String username, PublishBlogpostFormRequest request){
        Post post = getPost(request.getPostId());
        post.changeTitle(request.getTitle());
        post.changeContent(request.getContent());
        postRepository.save(post);
    }

    public void deletePost(Long postId, Authentication auth){
        checkAuthorPermission(postId, auth);
        postRepository.deleteById(postId);
    }

    public void checkAuthorPermission(Long postId, Authentication auth) {
        Post post = getPost(postId);
        if (!post.getMember().getEmail().address().equals(auth.getName())) {
            throw new SecurityException("You are not the author of this post");
        }
    }

}