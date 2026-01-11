package com.anthive.article.application.article.required;

import com.anthive.article.domain.article.Article;
import com.anthive.article.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByMember(Member member, Pageable pageable);

    @Query(
            value = "select article.id, article.title, article.content, article.board_id, article.member_id, " +
                    "article.publish_date " +
                    "from (" +
                    "   select id from article " +
                    "   where board_id = :boardId " +
                    "   order by id desc " +
                    "   limit :limit offset :offset " +
                    ") t left join article on t.id = article.id ",
            nativeQuery = true
    )
    List<Article> findAll(@Param("boardId") Long boardId, @Param("offset") Long offset, @Param("limit") Long limit);

    @Query(
            value = "select count(*) from (" +
                    "   select id from article where board_id = :boardId limit :limit" +
                    ") t",
            nativeQuery = true
    )
    Long count(@Param("boardId") Long boardId, @Param("limit") Long limit);
}