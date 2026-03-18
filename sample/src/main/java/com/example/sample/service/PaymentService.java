package com.example.sample.service;

import com.example.sample.dto.PaymentOrderRequest;
import com.example.sample.dto.PaymentOrderResponse;
import com.example.sample.dto.PaymentVerificationRequest;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Payment;
import org.json.JSONObject;
import com.example.sample.entity.PaymentRecord;
import com.example.sample.repository.PaymentRecordRepository;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private final PaymentRecordRepository paymentRecordRepository;

    public PaymentService(PaymentRecordRepository paymentRecordRepository) {
        this.paymentRecordRepository = paymentRecordRepository;
    }

    public PaymentOrderResponse createOrder(PaymentOrderRequest request) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", request.getAmount()); // amount in the smallest currency unit
        orderRequest.put("currency", request.getCurrency());
        
        String receipt = request.getReceiptId();
        if (receipt == null || receipt.isEmpty()) {
            receipt = "txn_" + UUID.randomUUID().toString().substring(0, 8);
        }
        orderRequest.put("receipt", receipt);

        Order order = client.orders.create(orderRequest);

        PaymentRecord record = new PaymentRecord();
        record.setUserId(request.getUserId());
        record.setRazorpayOrderId(order.get("id"));
        record.setAmount(request.getAmount());
        record.setCurrency(request.getCurrency());
        record.setStatus("PENDING");
        record.setCreatedAt(LocalDateTime.now());
        record.setUpdatedAt(LocalDateTime.now());
        paymentRecordRepository.save(record);

        PaymentOrderResponse response = new PaymentOrderResponse();
        response.setOrderId(order.get("id"));
        response.setAmount(order.get("amount"));
        response.setCurrency(order.get("currency"));
        response.setMessage("Created order successfully");

        return response;
    }

    public boolean verifySignature(PaymentVerificationRequest request) {
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", request.getRazorpayOrderId());
            options.put("razorpay_payment_id", request.getRazorpayPaymentId());
            options.put("razorpay_signature", request.getRazorpaySignature());

            boolean isValid = Utils.verifyPaymentSignature(options, keySecret);
            
            Optional<PaymentRecord> optionalRecord = paymentRecordRepository.findByRazorpayOrderId(request.getRazorpayOrderId());
            if (optionalRecord.isPresent()) {
                PaymentRecord record = optionalRecord.get();
                record.setRazorpayPaymentId(request.getRazorpayPaymentId());
                record.setStatus(isValid ? "SUCCESS" : "FAILED");
                record.setUpdatedAt(LocalDateTime.now());
                paymentRecordRepository.save(record);
            }

            return isValid;
        } catch (RazorpayException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Map<String, Object>> getAllPayments() throws Exception {
        RazorpayClient client = new RazorpayClient(keyId, keySecret);
        List<Payment> payments = client.payments.fetchAll();
        
        List<Map<String, Object>> result = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Payment payment : payments) {
            Map<String, Object> map = mapper.readValue(payment.toString(), Map.class);
            result.add(map);
        }
        return result;
    }
}
