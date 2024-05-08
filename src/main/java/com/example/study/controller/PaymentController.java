package com.example.study.controller;

import com.example.study.dto.PaymentDTO;
import com.example.study.entity.Order;
import com.example.study.service.OrderService;
import com.siot.IamportRestClient.IamportClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {

    @Autowired
    private RestTemplate restTemplate;

    private IamportClient iamportClient;

    @Autowired
    private OrderService orderService;

    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.secretkey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }

    @PostMapping("/portone-webhook")
    public ResponseEntity<String> handlePaymentWebhook(@RequestBody PaymentDTO paymentDTO) {
        try {
            // 요청의 body에서 imp_uid, merchant_uid 추출
            String imp_uid = paymentDTO.getImp_uid();
            String merchant_uid = paymentDTO.getMerchant_uid();

            // 1. 포트원 API 액세스 토큰 발급
            HttpHeaders tokenHeaders = new HttpHeaders();
            tokenHeaders.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> tokenRequestBody = new HashMap<>();
            tokenRequestBody.put("imp_key", apiKey);
            tokenRequestBody.put("imp_secret", secretKey);

            HttpEntity<Map<String, String>> tokenRequestEntity = new HttpEntity<>(tokenRequestBody, tokenHeaders);

            ResponseEntity<Map> tokenResponseEntity = restTemplate.postForEntity(
                    "https://api.iamport.kr/users/getToken",
                    tokenRequestEntity,
                    Map.class
            );

            if (!tokenResponseEntity.getStatusCode().is2xxSuccessful()) {
                throw new Exception("Token request failed");
            }

            String accessToken = (String) tokenResponseEntity.getBody().get("access_token");

            // 2. 포트원 결제내역 단건조회 API 호출
            HttpHeaders paymentHeaders = new HttpHeaders();
            paymentHeaders.set("Authorization", accessToken);

            ResponseEntity<Map> paymentResponseEntity = restTemplate.exchange(
                    "https://api.iamport.kr/payments/" + imp_uid,
                    HttpMethod.GET,
                    new HttpEntity<>(paymentHeaders),
                    Map.class
            );

            if (!paymentResponseEntity.getStatusCode().is2xxSuccessful()) {
                throw new Exception("Payment request failed");
            }

            Map<String, Object> payment = paymentResponseEntity.getBody();

            // 3. DB에서 결제되어야 하는 금액 조회
            Order order = orderService.findByMerchantUid(merchant_uid);
            int amountToBePaid = order.getAmount(); // 결제 되어야 하는 금액

            // 4. 결제 검증하기
            int amount = paymentDTO.getAmount();
            String status = paymentDTO.getStatus();
            // 결제금액 일치. 결제 된 금액 === 결제 되어야 하는 금액
            if (amount == amountToBePaid) {
                // DB에 결제 정보 저장
                orderService.updateOrder(paymentDTO);

                switch (status) {
                    case "paid": // 결제 완료
                        return ResponseEntity.ok().body("일반 결제 성공");
                }
            } else {
                // 결제금액 불일치. 위/변조 된 결제
                throw new Exception("위조된 결제 시도");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("요청 처리 실패");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}