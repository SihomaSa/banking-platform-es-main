package com.acme.banking.platform.accounts.domain.events;

import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class AccountOpened {
    private Long id;
    private String number;
    private BigDecimal overdraftLimit;
    private Long clientId;
    private LocalDateTime createdAt;
}
