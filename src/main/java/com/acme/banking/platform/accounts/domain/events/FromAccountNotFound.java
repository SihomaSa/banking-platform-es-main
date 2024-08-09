package com.acme.banking.platform.accounts.domain.events;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class FromAccountNotFound {
    protected Long transactionId;
    protected LocalDateTime createdAt;
}
