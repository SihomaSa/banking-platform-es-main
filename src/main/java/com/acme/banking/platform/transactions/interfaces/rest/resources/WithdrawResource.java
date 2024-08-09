package com.acme.banking.platform.transactions.interfaces.rest.resources;

import com.acme.banking.platform.transactions.domain.model.aggregates.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WithdrawResource(
    Long transactionId,
    Long accountId,
    BigDecimal amount,
    TransactionStatus status,
    LocalDateTime createdAt
) {}
