package org.example.paymentservice.model;

public enum PaymentStatus {
    CREATED,       // request received
    AUTHORIZED,    // funds reserved
    CAPTURED,      // funds taken
    FAILED,        // failed attempt
    REFUNDED,      // returned to customer
    CANCELED       // canceled before capture
}