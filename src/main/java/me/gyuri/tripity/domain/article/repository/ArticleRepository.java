package me.gyuri.tripity.domain.article.repository;

import me.gyuri.tripity.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
