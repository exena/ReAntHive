package com.anthive.article.application.article;

import com.anthive.article.application.article.required.IdGenerator;
import com.anthive.article.application.member.required.MemberRepository;
import com.anthive.article.application.article.provided.ArticleFinder;
import com.anthive.article.application.article.provided.ArticleModify;
import com.anthive.article.application.article.provided.ArticlePermission;
import com.anthive.article.application.article.required.ArticleRepository;
import com.anthive.article.domain.article.Article;
import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.article.ArticleNotFoundException;
import com.anthive.article.domain.article.PublishArticleRequest;
import com.anthive.article.domain.shared.Email;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class ArticleService implements ArticleFinder, ArticleModify, ArticlePermission {
    private final IdGenerator idGenerator;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public Page<Article> getAuthorsArticles(String username, Pageable pageable) {
        Member member = memberRepository.findByEmail(new Email(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return articleRepository.findByMember(member, pageable);
    }


    public Article getArticle(Long postId) {
        return articleRepository.findById(postId)
                .orElseThrow(() -> new ArticleNotFoundException("Post not found"));
    }

    public Article publishArticle(String username, PublishArticleRequest request){
        Member member = memberRepository.findByEmail(new Email(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        Article article = Article.of(idGenerator.nextId(), request, member);
        articleRepository.save(article);
        return article;
    }

    public void editArticle(String username, PublishArticleRequest request){
        Article article = getArticle(request.getArticleId());
        article.changeTitle(request.getTitle());
        article.changeContent(request.getContent());
        articleRepository.save(article);
    }

    public void deleteArticle(Long postId, String username){
        articleRepository.deleteById(postId);
    }

    public void checkAuthorPermission(Long postId, String username) {
        Article article = getArticle(postId);
        if (!article.getMember().getEmail().address().equals(username)) {
            throw new SecurityException("You are not the author of this post");
        }
    }

}