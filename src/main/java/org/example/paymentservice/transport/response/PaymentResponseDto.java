package org.example.paymentservice.transport.response;

import lombok.Builder;
import lombok.Data;
import org.example.paymentservice.model.PaymentMethod;
import org.example.paymentservice.model.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class PaymentResponseDto {
    private UUID id;
    private UUID userId;
    private BigDecimal amount;
    private String currency;
    private PaymentMethod method;
    private PaymentStatus status;
}
