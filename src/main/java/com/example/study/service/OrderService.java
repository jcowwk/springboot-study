package com.example.study.service;

import com.example.study.dto.PaymentDTO;
import com.example.study.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public Order findByMerchantUid(String merchantUid) {
        return new Order();
    }

    public void updateOrder(PaymentDTO paymentDTO) {
        return;
    }
}