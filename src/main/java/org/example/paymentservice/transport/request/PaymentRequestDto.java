package org.example.paymentservice.transport.request;

import lombok.Data;
import org.example.paymentservice.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRequestDto {
    private UUID userId;
    private BigDecimal amount;
    private String currency;
    private PaymentMethod method;
}
