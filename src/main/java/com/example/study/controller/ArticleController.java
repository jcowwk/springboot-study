package com.example.study.controller;

import com.example.study.dto.ArticleForm;
import com.example.study.entity.Article;
import com.example.study.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
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
        log.info(form.toString());
        // System.out.println(form.toString());

        // 1. DTO를 Entity로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        // System.out.println(article.toString());

        // 2. Repository에게 Entity를 DB로 저장하게 함
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        // System.out.println(saved.toString());

        return "articles/create";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id= "+id);

        // 1. id로 DB에서 데이터를 가져옴(entity)
        Article articleEntity = articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

        // 3. 보여줄 페이지 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){

        // 1. 모든 Article 가져오기
        List<Article> articleEntityList = (List<Article>)
        articleRepository.findAll();

        // 2. 가져온 Article 리스트를 view로 전달(Model 사용)
        model.addAttribute("articleList", articleEntityList);

        // 3. view 페이지 설정
        return "articles/index";
    }
    
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());

        // 1. DTO -> Entity
        Article articleEntity = form.toEntity();

        // 2. Entity를 db에 저장
        // 2-1. db에 기존 데이터를 가져와
        // 가져오려는 값이 없는 경우 null 반환
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2. 기존 데이터가 있다면 값을 갱신
        if(target != null){
            articleRepository.save(articleEntity);
        }

        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){

        // 1. 삭제 대상을 가져와
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 대상 삭제
        if(target != null){
            articleRepository.delete(target);
            // 삭제 완료 메시지 출력을 위한 메소드
            rttr.addAttribute("msg", target.getId() + "번 글이 삭제되었습니다.");
        }

        // 3. 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }
}