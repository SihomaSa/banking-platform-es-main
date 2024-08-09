package com.acme.banking.platform.transactions.application.sagas;

import com.acme.banking.platform.accounts.domain.commands.CreditAccount;
import com.acme.banking.platform.accounts.domain.commands.CreditFromAccount;
import com.acme.banking.platform.accounts.domain.commands.DebitAccount;
import com.acme.banking.platform.accounts.domain.events.*;
import com.acme.banking.platform.accounts.domain.processors.AccountNumber;
import com.acme.banking.platform.accounts.infrastructure.persistence.jpa.repositories.AccountNumberRepository;
import com.acme.banking.platform.transactions.domain.commands.MarkTransferAsCompleted;
import com.acme.banking.platform.transactions.domain.commands.MarkTransferAsFailed;
import com.acme.banking.platform.transactions.domain.events.TransferStarted;
import com.acme.banking.platform.transactions.domain.model.valueobjects.TransactionType;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;

@Saga
public class TransferMoneySaga {
    private Long transactionId;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;

    private transient CommandGateway commandGateway;
    private transient AccountNumberRepository accountNumberRepository;
    private transient EventBus eventBus;

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Autowired
    public void setAccountNumberRepository(AccountNumberRepository accountNumberRepository) {
        this.accountNumberRepository = accountNumberRepository;
    }

    @Autowired
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(TransferStarted event) {
        transactionId = event.getTransactionId();
        fromAccountId = event.getFromAccountId();
        toAccountId = event.getToAccountId();
        amount = event.getAmount();
        Optional<AccountNumber> accountNumber = accountNumberRepository.findById(fromAccountId);
        if (accountNumber.isEmpty()) {
            LocalDateTime createdAt = LocalDateTime.now();
            FromAccountNotFound fromAccountNotFound = new FromAccountNotFound(
                transactionId,
                createdAt
            );
            eventBus.publish(asEventMessage((fromAccountNotFound)));
            return;
        }
        DebitAccount command = new DebitAccount(
            event.getFromAccountId(),
            event.getTransactionId(),
            event.getAmount(),
            TransactionType.TRANSFER,
            event.getCreatedAt()
        );
        commandGateway.send(command);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(FromAccountNotFound event) {
        LocalDateTime updatedAt = LocalDateTime.now();
        MarkTransferAsFailed command = new MarkTransferAsFailed(
            event.getTransactionId(),
            updatedAt
        );
        commandGateway.send(command);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(InsufficientFundsDetected event) {
        LocalDateTime updatedAt = LocalDateTime.now();
        MarkTransferAsFailed command = new MarkTransferAsFailed(
                event.getTransactionId(),
                updatedAt
        );
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(AccountDebited event) {
        Optional<AccountNumber> accountNumber = accountNumberRepository.findById(toAccountId);
        if (accountNumber.isEmpty()) {
            LocalDateTime createdAt = LocalDateTime.now();
            ToAccountNotFound toAccountNotFound = new ToAccountNotFound(
                transactionId,
                createdAt
            );
            eventBus.publish(asEventMessage((toAccountNotFound)));
            return;
        }
        LocalDateTime updatedAt = LocalDateTime.now();
        CreditAccount command = new CreditAccount(
            toAccountId,
            event.getTransactionId(),
            event.getAmount(),
            TransactionType.TRANSFER,
            updatedAt
        );
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(ToAccountNotFound event) {
        LocalDateTime updatedAt = LocalDateTime.now();
        CreditFromAccount command = new CreditFromAccount(
            fromAccountId,
            event.getTransactionId(),
            amount,
            TransactionType.TRANSFER,
            updatedAt
        );
        commandGateway.send(command);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(FromAccountCredited event) {
        LocalDateTime updatedAt = LocalDateTime.now();
        MarkTransferAsFailed command = new MarkTransferAsFailed(
            event.getTransactionId(),
            updatedAt
        );
        commandGateway.send(command);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(AccountCredited event) {
        LocalDateTime updatedAt = LocalDateTime.now();
        MarkTransferAsCompleted command = new MarkTransferAsCompleted(
            event.getTransactionId(),
            updatedAt
        );
        commandGateway.send(command);
    }
}
