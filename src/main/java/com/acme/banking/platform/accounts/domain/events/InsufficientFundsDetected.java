package com.acme.banking.platform.accounts.domain.events;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class InsufficientFundsDetected {
    private Long accountId;
    private Long transactionId;
    private LocalDateTime createdAt;
}
