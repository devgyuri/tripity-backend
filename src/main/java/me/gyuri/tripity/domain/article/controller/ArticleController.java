package me.gyuri.tripity.domain.article.controller;

import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.article.dto.AddArticleRequest;
import me.gyuri.tripity.domain.article.dto.ArticleViewResponse;
import me.gyuri.tripity.domain.article.dto.UpdateArticleRequest;
import me.gyuri.tripity.domain.article.entity.Article;
import me.gyuri.tripity.domain.article.service.ArticleService;
import me.gyuri.tripity.domain.user.entity.User;
import me.gyuri.tripity.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleViewResponse>> findAllArticles() {
        List<ArticleViewResponse> articles = articleService.findAll()
                .stream()
                .map(ArticleViewResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleViewResponse> findArticle(@PathVariable(value = "id") long id) {
        Article article = articleService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleViewResponse(article));
    }

    @PostMapping("/api/articles")
    public ResponseEntity<ArticleViewResponse> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        Article savedArticle = articleService.save(request, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ArticleViewResponse(savedArticle));
    }


    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable(value = "id") long id) {
        articleService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleViewResponse> updateArticle(@PathVariable(value = "id") long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = articleService.update(id, request);

        return ResponseEntity.ok()
                .body(new ArticleViewResponse(updatedArticle));
    }
}
