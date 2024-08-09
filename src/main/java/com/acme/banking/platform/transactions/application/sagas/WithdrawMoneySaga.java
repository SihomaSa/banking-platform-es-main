package com.acme.banking.platform.transactions.application.sagas;

import com.acme.banking.platform.accounts.domain.commands.DebitAccount;
import com.acme.banking.platform.accounts.domain.events.AccountDebited;
import com.acme.banking.platform.accounts.domain.events.AccountNotFound;
import com.acme.banking.platform.accounts.domain.events.InsufficientFundsDetected;
import com.acme.banking.platform.accounts.domain.processors.AccountNumber;
import com.acme.banking.platform.accounts.infrastructure.persistence.jpa.repositories.AccountNumberRepository;
import com.acme.banking.platform.transactions.domain.commands.MarkWithdrawAsCompleted;
import com.acme.banking.platform.transactions.domain.commands.MarkWithdrawAsFailed;
import com.acme.banking.platform.transactions.domain.events.WithdrawStarted;
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
public class WithdrawMoneySaga {
    private Long transactionId;
    private Long accountId;
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
    public void on(WithdrawStarted event) {
        transactionId = event.getTransactionId();
        accountId = event.getAccountId();
        amount = event.getAmount();

        Optional<AccountNumber> accountNumber = accountNumberRepository.findById(accountId);
        if (accountNumber.isPresent()) {
            DebitAccount command = new DebitAccount(
                event.getAccountId(),
                event.getTransactionId(),
                event.getAmount(),
                TransactionType.WITHDRAWAL,
                event.getCreatedAt()
            );
            commandGateway.send(command);
            return;
        }
        LocalDateTime createdAt = LocalDateTime.now();
        AccountNotFound accountNotFound = new AccountNotFound(transactionId, createdAt);
        eventBus.publish(asEventMessage((accountNotFound)));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(AccountNotFound event) {
        LocalDateTime updatedAt = LocalDateTime.now();
        MarkWithdrawAsFailed command = new MarkWithdrawAsFailed(
            event.getTransactionId(),
            updatedAt
        );
        commandGateway.send(command);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(InsufficientFundsDetected event) {
        LocalDateTime updatedAt = LocalDateTime.now();
        MarkWithdrawAsFailed command = new MarkWithdrawAsFailed(
            event.getTransactionId(),
            updatedAt
        );
        commandGateway.send(command);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(AccountDebited event) {
        LocalDateTime updatedAt = LocalDateTime.now();
        MarkWithdrawAsCompleted command = new MarkWithdrawAsCompleted(
            event.getTransactionId(),
            updatedAt
        );
        commandGateway.send(command);
    }
}
