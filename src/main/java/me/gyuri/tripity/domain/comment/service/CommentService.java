package me.gyuri.tripity.domain.comment.service;

import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.article.entity.Article;
import me.gyuri.tripity.domain.article.service.ArticleService;
import me.gyuri.tripity.domain.comment.dto.AddCommentRequest;
import me.gyuri.tripity.domain.comment.entity.Comment;
import me.gyuri.tripity.domain.comment.repository.CommentRepository;
import me.gyuri.tripity.domain.user.entity.User;
import me.gyuri.tripity.domain.user.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final ArticleService articleService;
    private final CommentRepository commentRepository;
    private final UserService userService;

    public Comment addComment(AddCommentRequest request) {
        String email = getAuthorEmail();
        User user = userService.findByEmail(email);
        Article article = articleService.findById(request.getArticleId());

        return commentRepository.save(request.toEntity(article, user));
    }

    private static String getAuthorEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
