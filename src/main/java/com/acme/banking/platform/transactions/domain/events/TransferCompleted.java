package com.acme.banking.platform.transactions.domain.events;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class TransferCompleted {
    private Long transactionId;
    private LocalDateTime updatedAt;
}
