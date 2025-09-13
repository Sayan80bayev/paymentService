package org.example.paymentservice.mapper;

import org.example.paymentservice.model.Payment;
import org.example.paymentservice.model.Transaction;
import org.example.paymentservice.transport.request.PaymentRequestDto;
import org.example.paymentservice.transport.response.PaymentResponseDto;
import org.example.paymentservice.transport.response.TransactionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    // Request DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    Payment toEntity(PaymentRequestDto dto);

    // Entity -> Response DTO
    PaymentResponseDto toDto(Payment payment);

    // Transaction -> Response DTO
    @Mapping(target = "paymentId", source = "payment.id")
    TransactionResponseDto toDto(Transaction transaction);
}