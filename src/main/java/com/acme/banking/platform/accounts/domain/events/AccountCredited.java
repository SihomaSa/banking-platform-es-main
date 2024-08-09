package com.acme.banking.platform.accounts.domain.events;

import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class AccountCredited {
    private Long accountId;
    private Long transactionId;
    private BigDecimal amount;
    private LocalDateTime updatedAt;
}
