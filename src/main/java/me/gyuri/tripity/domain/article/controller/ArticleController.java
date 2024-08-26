package me.gyuri.tripity.domain.article.controller;

import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.article.dto.AddArticleRequest;
import me.gyuri.tripity.domain.article.dto.ArticleViewResponse;
import me.gyuri.tripity.domain.article.dto.UpdateArticleRequest;
import me.gyuri.tripity.domain.article.entity.Article;
import me.gyuri.tripity.domain.article.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleController {
    private final ArticleService articleService;

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
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        Article savedArticle = articleService.save(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }


    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable(value = "id") long id) {
        articleService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable(value = "id") long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = articleService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
