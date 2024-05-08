package com.example.study.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="order")
@AllArgsConstructor
@RequiredArgsConstructor
public class Order {
    @Id
    private String merchant_uid; // 주문 고유 번호

    private String imp_uid; // 아임포트 고유 번호
    private String pay_method; // 결제 수단
    private int amount; // 결제 금액
    private String buyer_email; // 구매자 이메일
    private String buyer_name; // 구매자 이름
    private String buyer_tel; // 구매자 전화번호
    private String buyer_addr; // 구매자 주소
    private String buyer_postcode; // 구매자 우편번호
}
