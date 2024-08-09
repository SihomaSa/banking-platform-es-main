package com.acme.banking.platform.accounts.domain.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class OpenAccount {
    @TargetAggregateIdentifier
    private Long id;
    private String number;
    private BigDecimal overdraftLimit;
    private Long clientId;
    private LocalDateTime createdAt;
}
