package me.gyuri.tripity.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gyuri.tripity.domain.article.entity.Article;
import me.gyuri.tripity.domain.comment.entity.Comment;
import me.gyuri.tripity.domain.user.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequest {
    private Long articleId;
    private String content;

    public Comment toEntity(Article article, User user) {
        return Comment.builder()
                .article(article)
                .content(content)
                .user(user)
                .build();
    }
}
