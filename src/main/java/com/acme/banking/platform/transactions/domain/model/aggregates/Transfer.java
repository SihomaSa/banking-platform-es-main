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
public class Transfer extends Transaction {
    private Long fromAccountId;
    private Long toAccountId;

    protected Transfer() {
    }

    @CommandHandler
    public Transfer(TransferMoney command) {
        TransferStarted event = new TransferStarted(
            command.getTransactionId(),
            command.getFromAccountId(),
            command.getToAccountId(),
            command.getAmount(),
            command.getCreatedAt()
        );
        apply(event);
    }

    @CommandHandler
    public void handle(MarkTransferAsCompleted command) {
        TransferCompleted event = new TransferCompleted(
            command.getTransactionId(),
            command.getUpdatedAt()
        );
        apply(event);
    }

    @CommandHandler
    public void handle(MarkTransferAsFailed command) {
        TransferFailed event = new TransferFailed(
            command.getTransactionId(),
            command.getUpdatedAt()
        );
        apply(event);
    }

    @EventSourcingHandler
    public void on(TransferStarted event) {
        this.id = event.getTransactionId();
        this.amount = Money.of(event.getAmount(), "USD");
        this.type = TransactionType.TRANSFER;
        this.status = TransactionStatus.STARTED;
        this.fromAccountId = event.getFromAccountId();
        this.toAccountId = event.getToAccountId();
    }

    @EventSourcingHandler
    public void on(TransferCompleted event) {
        this.status = TransactionStatus.COMPLETED;
    }

    @EventSourcingHandler
    public void on(TransferFailed event) {
        this.status = TransactionStatus.FAILED;
    }
}
