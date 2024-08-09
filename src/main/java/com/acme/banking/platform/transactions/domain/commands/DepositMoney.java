package com.acme.banking.platform.transactions.domain.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class DepositMoney {
    @TargetAggregateIdentifier
    private Long transactionId;
    private Long accountId;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
