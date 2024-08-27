package me.gyuri.tripity.domain.article.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gyuri.tripity.domain.article.entity.Article;
import me.gyuri.tripity.domain.user.dto.UserInfo;
import me.gyuri.tripity.domain.user.entity.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {
    private Long id;
    private String title;
    private String content;
    private UserInfo userInfo;
    private LocalDateTime createdAt;


    public ArticleViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.userInfo = UserInfo.from(article.getUser());
        this.createdAt = article.getCreatedAt();
    }
}
