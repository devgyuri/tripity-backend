package me.gyuri.tripity.domain.article.dto;

import lombok.Getter;
import me.gyuri.tripity.domain.article.entity.Article;

@Getter
public class ArticleResponse {
    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
