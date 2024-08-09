package com.acme.banking.platform.accounts.domain.events;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class ToAccountNotFound {
    protected Long transactionId;
    protected LocalDateTime createdAt;
}
