package org.example.paymentservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentType type; // COINS | SUBSCRIPTION | GIFT

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status; // PENDING | SUCCEEDED | FAILED

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amountUsd;

    @Column(nullable = false, length = 3)
    private String currency; // ISO-4217 (USD, EUR, ...)

    @Column(nullable = false)
    private String productId; // e.g. "coin_pack_10", "plan-premium-monthly"

    @Lob
    @Column(columnDefinition = "jsonb")
    private String details;
    // JSON с дополнительной инфой:
    // - для COINS: { "coinsPurchased": 50 }
    // - для SUBSCRIPTION: { "periodStart": "...", "periodEnd": "...", "autoRenew": true }
    // - для GIFT: { "giftProductId": "...", "toUserId": "...", "message": "..." }

    private String providerPaymentId; // ID транзакции в банке/провайдере

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;
}