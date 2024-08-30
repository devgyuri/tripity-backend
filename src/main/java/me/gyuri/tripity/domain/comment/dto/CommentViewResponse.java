package me.gyuri.tripity.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gyuri.tripity.domain.article.entity.Article;
import me.gyuri.tripity.domain.comment.entity.Comment;
import me.gyuri.tripity.domain.user.dto.UserInfo;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CommentViewResponse {
    private Long id;
    private String content;
    private UserInfo userInfo;
    private LocalDateTime createdAt;
    private Long articleId;

    public CommentViewResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userInfo = UserInfo.from(comment.getUser());
        this.createdAt = comment.getCreatedAt();
        this.articleId = comment.getArticle().getId();
    }
}
