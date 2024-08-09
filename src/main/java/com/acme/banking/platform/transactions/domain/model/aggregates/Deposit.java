package com.acme.banking.platform.transactions.domain.model.aggregates;

import com.acme.banking.platform.transactions.domain.commands.*;
import com.acme.banking.platform.transactions.domain.events.*;
import com.acme.banking.platform.transactions.domain.model.valueobjects.TransactionType;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.javamoney.moneta.Money;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Deposit extends Transaction {
    private Long accountId;

    protected Deposit() {
    }

    @CommandHandler
    public Deposit(DepositMoney command) {
        DepositStarted event = new DepositStarted(
            command.getTransactionId(),
            command.getAccountId(),
            command.getAmount(),
            command.getCreatedAt()
        );
        apply(event);
    }

    @CommandHandler
    public void handle(MarkDepositAsCompleted command) {
        DepositCompleted event = new DepositCompleted(
            command.getTransactionId(),
            command.getUpdatedAt()
        );
        apply(event);
    }

    @CommandHandler
    public void handle(MarkDepositAsFailed command) {
        DepositFailed event = new DepositFailed(
            command.getTransactionId(),
            command.getUpdatedAt()
        );
        apply(event);
    }

    @EventSourcingHandler
    public void on(DepositStarted event) {
        this.id = event.getTransactionId();
        this.accountId = event.getAccountId();
        this.amount = Money.of(event.getAmount(), "USD");
        this.type = TransactionType.DEPOSIT;
        this.status = TransactionStatus.STARTED;
        this.accountId = event.getAccountId();
    }

    @EventSourcingHandler
    public void on(DepositCompleted event) {
        this.status = TransactionStatus.COMPLETED;
    }

    @EventSourcingHandler
    public void on(DepositFailed event) {
        this.status = TransactionStatus.FAILED;
    }
}
