package com.acme.banking.platform.transactions.interfaces.rest.resources;

import com.acme.banking.platform.transactions.domain.model.aggregates.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferResource(
    Long transactionId,
    Long fromAccountId,
    Long toAccountId,
    BigDecimal amount,
    TransactionStatus status,
    LocalDateTime createdAt
) {}
