package me.gyuri.tripity.domain.comment.service;

import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.article.entity.Article;
import me.gyuri.tripity.domain.article.service.ArticleService;
import me.gyuri.tripity.domain.comment.dto.AddCommentRequest;
import me.gyuri.tripity.domain.comment.entity.Comment;
import me.gyuri.tripity.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final ArticleService articleService;
    private final CommentRepository commentRepository;

    public Comment addComment(AddCommentRequest request) {
        Article article = articleService.findById(request.getArticleId());

        return commentRepository.save(request.toEntity(article));
    }
}
