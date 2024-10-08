package me.gyuri.tripity.domain.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gyuri.tripity.domain.article.entity.Article;
import me.gyuri.tripity.domain.user.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity(User user) {
        return Article.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
