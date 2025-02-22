package com.pvh.gym_management.services;

import java.util.Map;

public interface PaymentService {
    String handleVnpayReturn(Map<String, String> params);
    String createPaymentUrl(long amount, String orderInfo) throws Exception;
    boolean validateSignature(Map<String, String> params, String secureHash);
}
