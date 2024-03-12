package com.example.study.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data // getter setter
@Builder // dto -> entity화
@AllArgsConstructor // 모든 컬럼 생성자 생성
@NoArgsConstructor // 기본 생성자
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 id 생성
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String username;

    @Column(nullable = false)
    private String password;
}
