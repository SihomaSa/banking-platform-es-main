package com.acme.banking.platform.transactions.domain.events;

import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class TransferStarted {
    private Long transactionId;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
