package org.example.paymentservice.contoller;

import lombok.RequiredArgsConstructor;
import org.example.paymentservice.service.PaymentService;
import org.example.paymentservice.transport.request.PaymentRequestDto;
import org.example.paymentservice.transport.response.PaymentResponseDto;
import org.example.paymentservice.transport.response.TransactionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // Create a new payment
    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody PaymentRequestDto request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    // Capture payment by ID
    @PostMapping("/{paymentId}/capture")
    public ResponseEntity<PaymentResponseDto> capturePayment(@PathVariable UUID paymentId) {
        return ResponseEntity.ok(paymentService.capturePayment(paymentId));
    }

    // Refund payment
    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentResponseDto> refundPayment(
            @PathVariable UUID paymentId,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(paymentService.refundPayment(paymentId, amount));
    }

    // Get all payments for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsForUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(paymentService.getPaymentsForUser(userId));
    }

    // Get all transactions for a payment
    @GetMapping("/{paymentId}/transactions")
    public ResponseEntity<List<TransactionResponseDto>> getTransactionsForPayment(@PathVariable UUID paymentId) {
        return ResponseEntity.ok(paymentService.getTransactionsForPayment(paymentId));
    }
}