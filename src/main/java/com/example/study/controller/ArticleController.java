package com.example.study.controller;

import com.example.study.dto.ArticleForm;
import com.example.study.entity.Article;
import com.example.study.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    // 스프링 부트가 미리 생성 해놓은 객체를 가져 다가 자동 연결
    @Autowired
    private ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        System.out.println(form.toString());

        // 1. DTO를 Entity로 변환
        Article article = form.toEntity();
        System.out.println(article.toString());

        // 2. Repository에게 Entity를 DB로 저장하게 함
        Article saved = articleRepository.save(article);
        System.out.println(saved.toString());

        return "";
    }
}