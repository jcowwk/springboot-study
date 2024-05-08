package com.example.study.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private String imp_uid; // 아임포트 고유 번호
    private String merchant_uid; // 주문 고유 번호
    private String pay_method; // 결제 수단
    private int amount; // 결제 금액
    private String buyer_email; // 구매자 이메일
    private String buyer_name; // 구매자 이름
    private String buyer_tel; // 구매자 전화번호
    private String buyer_addr; // 구매자 주소
    private String buyer_postcode; // 구매자 우편번호
    private String status;
}