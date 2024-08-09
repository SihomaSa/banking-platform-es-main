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
public class Withdrawal extends Transaction {
    private Long accountId;

    protected Withdrawal() {
    }

    @CommandHandler
    public Withdrawal(WithdrawMoney command) {
        WithdrawStarted event = new WithdrawStarted(
            command.getTransactionId(),
            command.getAccountId(),
            command.getAmount(),
            command.getCreatedAt()
        );
        apply(event);
    }

    @CommandHandler
    public void handle(MarkWithdrawAsCompleted command) {
        WithdrawCompleted event = new WithdrawCompleted(
            command.getTransactionId(),
            command.getUpdatedAt()
        );
        apply(event);
    }

    @CommandHandler
    public void handle(MarkWithdrawAsFailed command) {
        DepositFailed event = new DepositFailed(
            command.getTransactionId(),
            command.getUpdatedAt()
        );
        apply(event);
    }

    @EventSourcingHandler
    public void on(WithdrawStarted event) {
        this.id = event.getTransactionId();
        this.amount = Money.of(event.getAmount(), "USD");
        this.type = TransactionType.WITHDRAWAL;
        this.status = TransactionStatus.STARTED;
        this.accountId = event.getAccountId();
    }

    @EventSourcingHandler
    public void on(WithdrawCompleted event) {
        this.status = TransactionStatus.COMPLETED;
    }

    @EventSourcingHandler
    public void on(WithdrawFailed event) {
        this.status = TransactionStatus.FAILED;
    }
}
