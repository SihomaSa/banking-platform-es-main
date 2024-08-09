package com.acme.banking.platform.accounts.domain.events;

import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class AccountEdited {
    private Long id;
    private BigDecimal overdraftLimit;
    private LocalDateTime updatedAt;
}
