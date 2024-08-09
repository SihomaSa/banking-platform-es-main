package com.acme.banking.platform.transactions.application.sagas;

import com.acme.banking.platform.accounts.domain.commands.CreditAccount;
import com.acme.banking.platform.accounts.domain.events.AccountCredited;
import com.acme.banking.platform.accounts.domain.events.AccountNotFound;
import com.acme.banking.platform.accounts.domain.processors.AccountNumber;
import com.acme.banking.platform.accounts.infrastructure.persistence.jpa.repositories.AccountNumberRepository;
import com.acme.banking.platform.transactions.domain.commands.MarkDepositAsCompleted;
import com.acme.banking.platform.transactions.domain.commands.MarkDepositAsFailed;
import com.acme.banking.platform.transactions.domain.events.DepositStarted;
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
public class DepositMoneySaga {
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
    public void on(DepositStarted event) {
        transactionId = event.getTransactionId();
        accountId = event.getAccountId();
        amount = event.getAmount();

        Optional<AccountNumber> accountNumber = accountNumberRepository.findById(accountId);
        if (accountNumber.isPresent()) {
            CreditAccount command = new CreditAccount(
                event.getAccountId(),
                event.getTransactionId(),
                event.getAmount(),
                TransactionType.DEPOSIT,
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
        MarkDepositAsFailed command = new MarkDepositAsFailed(
            this.transactionId,
            updatedAt
        );
        commandGateway.send(command);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(AccountCredited event) {
        LocalDateTime updatedAt = LocalDateTime.now();
        MarkDepositAsCompleted command = new MarkDepositAsCompleted(
            this.transactionId,
            updatedAt
        );
        commandGateway.send(command);
    }
}
