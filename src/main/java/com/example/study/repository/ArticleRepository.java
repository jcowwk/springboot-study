package com.example.study.repository;

import com.example.study.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
}