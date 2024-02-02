package com.example.study.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

// Entity를 붙여야 DB가 해당 객체를 인식 가능
@Entity
@Embeddable
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;

    @Column
    private String content;

    public Article(Long id, String title, String content){
        this.id=id;
        this.title=title;
        this.content=content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
