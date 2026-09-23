package com.paypalclone.transactionservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SendMoneyRequest {
    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    private String message;
}
