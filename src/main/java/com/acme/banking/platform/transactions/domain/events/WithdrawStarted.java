package com.acme.banking.platform.transactions.domain.events;

import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class WithdrawStarted {
    private Long transactionId;
    private Long accountId;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
