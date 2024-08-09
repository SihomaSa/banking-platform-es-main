package com.acme.banking.platform.transactions.domain.model.aggregates;

import com.acme.banking.platform.transactions.domain.model.valueobjects.TransactionType;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import javax.money.MonetaryAmount;

@Aggregate
public abstract class Transaction {
    @AggregateIdentifier
    protected Long id;
    protected MonetaryAmount amount;
    protected TransactionType type;
    protected TransactionStatus status;
}
