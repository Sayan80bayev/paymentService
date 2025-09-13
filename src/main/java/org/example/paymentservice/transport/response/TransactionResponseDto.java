package org.example.paymentservice.transport.response;

import lombok.Builder;
import lombok.Data;
import org.example.paymentservice.model.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;
import java.time.Instant;

@Data
@Builder
public class TransactionResponseDto {
    private UUID id;
    private UUID paymentId;
    private TransactionType type;
    private BigDecimal amount;
    private Instant createdAt;
}
