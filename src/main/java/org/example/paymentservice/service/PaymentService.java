package org.example.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.example.paymentservice.mapper.PaymentMapper;
import org.example.paymentservice.model.Payment;
import org.example.paymentservice.model.PaymentStatus;
import org.example.paymentservice.model.Transaction;
import org.example.paymentservice.model.TransactionType;
import org.example.paymentservice.repository.PaymentRepository;
import org.example.paymentservice.repository.TransactionRepository;
import org.example.paymentservice.transport.request.PaymentRequestDto;
import org.example.paymentservice.transport.response.PaymentResponseDto;
import org.example.paymentservice.transport.response.TransactionResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto request) {
        Payment payment = paymentMapper.toEntity(request);
        Payment savedPayment = paymentRepository.save(payment);

        Transaction txn = Transaction.builder()
                .payment(savedPayment)
                .type(TransactionType.AUTHORIZATION)
                .amount(savedPayment.getAmountUsd())
                .createdAt(Instant.now())
                .build();
        transactionRepository.save(txn);

        return paymentMapper.toDto(savedPayment);
    }

    @Transactional
    public PaymentResponseDto capturePayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.CAPTURED);
        payment.setUpdatedAt(Instant.now());
        Payment updatedPayment = paymentRepository.save(payment);

        Transaction txn = Transaction.builder()
                .payment(updatedPayment)
                .type(TransactionType.CAPTURE)
                .amount(payment.getAmountUsd())
                .createdAt(Instant.now())
                .build();
        transactionRepository.save(txn);

        return paymentMapper.toDto(updatedPayment);
    }

    @Transactional
    public PaymentResponseDto refundPayment(UUID paymentId, BigDecimal refundAmount) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setUpdatedAt(Instant.now());
        Payment updatedPayment = paymentRepository.save(payment);

        Transaction txn = Transaction.builder()
                .payment(updatedPayment)
                .type(TransactionType.REFUND)
                .amount(refundAmount)
                .createdAt(Instant.now())
                .build();
        transactionRepository.save(txn);

        return paymentMapper.toDto(updatedPayment);
    }

    public List<PaymentResponseDto> getPaymentsForUser(UUID userId) {
        return paymentRepository.findByUserId(userId)
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    public List<TransactionResponseDto> getTransactionsForPayment(UUID paymentId) {
        return transactionRepository.findByPaymentId(paymentId)
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }
}