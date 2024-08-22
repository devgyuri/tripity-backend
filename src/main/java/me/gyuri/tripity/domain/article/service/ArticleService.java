package me.gyuri.tripity.domain.article.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.article.dto.AddArticleRequest;
import me.gyuri.tripity.domain.article.dto.UpdateArticleRequest;
import me.gyuri.tripity.domain.article.entity.Article;
import me.gyuri.tripity.domain.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findById(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public Article save(AddArticleRequest request) {
        return articleRepository.save(request.toEntity());
    }

    public void delete(long id) {
        articleRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
