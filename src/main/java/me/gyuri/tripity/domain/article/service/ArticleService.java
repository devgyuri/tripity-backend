package me.gyuri.tripity.domain.article.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.article.dto.AddArticleRequest;
import me.gyuri.tripity.domain.article.dto.UpdateArticleRequest;
import me.gyuri.tripity.domain.article.entity.Article;
import me.gyuri.tripity.domain.article.repository.ArticleRepository;
import me.gyuri.tripity.domain.user.entity.User;
import me.gyuri.tripity.domain.user.service.UserService;
import me.gyuri.tripity.global.exception.CustomException;
import me.gyuri.tripity.global.exception.ErrorCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findById(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public Article addArticle(AddArticleRequest request, String email) {
        User user = userService.findByEmail(email);
        return articleRepository.save(request.toEntity(user));
    }

    public void deleteArticle(long id) {
        Article article = articleRepository.findById(id)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ARTICLE));

        authorizeArticleAuthor(article);
        articleRepository.deleteById(id);
    }

    @Transactional
    public Article updateArticle(long id, UpdateArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    private static void authorizeArticleAuthor(Article article) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getUser().getEmail().equals(email)) {
            throw new CustomException(ErrorCode.NOT_MATCH_AUTHOR);
        }
    }
}
