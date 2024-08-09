package com.acme.banking.platform.accounts.domain.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class EditAccount {
    @TargetAggregateIdentifier
    private Long id;
    private BigDecimal overdraftLimit;
    private LocalDateTime updatedAt;
}
