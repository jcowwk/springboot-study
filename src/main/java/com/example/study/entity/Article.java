package com.example.study.entity;

import jakarta.persistence.*;
import lombok.*;

// Entity를 붙여야 DB가 해당 객체를 인식 가능
@Entity
@AllArgsConstructor
@ToString
@Setter
@Getter
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

    // @ALLArgsConstructor과 똑같은 기능
//    public Article(Long id, String title, String content){
//        this.id=id;
//        this.title=title;
//        this.content=content;
//    }

    // @ToSring과 똑같은 기능
//    @Override
//    public String toString() {
//        return "Article{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }
}
