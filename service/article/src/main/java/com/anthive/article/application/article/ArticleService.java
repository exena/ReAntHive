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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;


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

    public ArticlePageResponse readAll(Long boardId, Long page, Long pageSize) {
        return ArticlePageResponse.of(
                articleRepository.findAll(boardId, (page - 1) * pageSize, pageSize).stream()
                        .map(ArticleResponse::from)
                        .toList(),
                articleRepository.count(
                        boardId,
                        PageLimitCalculator.calculatePageLimit(page, pageSize, 10L)
                )
        );
    }

    public Page<Article> readAllPage(Long boardId, Long page, Long pageSize) {
        long offset = (page - 1) * pageSize;

        List<Article> content =
                articleRepository.findAll(boardId, offset, pageSize);

        long total =
                articleRepository.count(
                        boardId,
                        PageLimitCalculator.calculatePageLimit(page, pageSize, 10L)
                );

        PageRequest pageRequest =
                PageRequest.of(page.intValue() - 1, pageSize.intValue());

        return new PageImpl<>(content, pageRequest, total);
    }

    public List<ArticleResponse> readAllInfiniteScroll(Long boardId, Long pageSize, Long lastArticleId) {
        List<Article> articles = lastArticleId == null ?
                articleRepository.findAllInfiniteScroll(boardId, pageSize) :
                articleRepository.findAllInfiniteScroll(boardId, pageSize, lastArticleId);
        return articles.stream().map(ArticleResponse::from).toList();
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