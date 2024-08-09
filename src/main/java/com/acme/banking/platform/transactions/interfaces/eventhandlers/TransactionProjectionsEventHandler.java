package com.acme.banking.platform.transactions.interfaces.eventhandlers;

import com.acme.banking.platform.accounts.domain.events.AccountCredited;
import com.acme.banking.platform.accounts.domain.events.AccountDebited;
import com.acme.banking.platform.transactions.domain.events.*;
import com.acme.banking.platform.transactions.domain.model.aggregates.TransactionStatus;
import com.acme.banking.platform.transactions.domain.model.valueobjects.TransactionType;
import com.acme.banking.platform.transactions.domain.projections.TransactionProjection;
import com.acme.banking.platform.transactions.infrastructure.persistence.jpa.repositories.TransactionRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class TransactionProjectionsEventHandler {
    private final TransactionRepository transactionRepository;

    public TransactionProjectionsEventHandler(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @EventHandler
    public void on(DepositStarted event) {
        TransactionProjection transaction = new TransactionProjection(
            event.getTransactionId(),
            event.getAccountId(),
 null,
            event.getAmount(),
            TransactionType.DEPOSIT.toString(),
            TransactionStatus.STARTED.toString(),
            event.getCreatedAt(),
  null
        );
        transactionRepository.save(transaction);
    }

    @EventHandler
    public void on(WithdrawStarted event) {
        TransactionProjection transaction = new TransactionProjection(
            event.getTransactionId(),
            event.getAccountId(),
 null,
            event.getAmount(),
            TransactionType.WITHDRAWAL.toString(),
            TransactionStatus.STARTED.toString(),
            event.getCreatedAt(),
  null);
        transactionRepository.save(transaction);
    }

    @EventHandler
    public void on(TransferStarted event) {
        TransactionProjection transactionView = new TransactionProjection(
            event.getTransactionId(),
            event.getFromAccountId(),
            event.getToAccountId(),
            event.getAmount(),
            TransactionType.TRANSFER.toString(),
            TransactionStatus.STARTED.toString(),
            event.getCreatedAt(),
  null
        );
        transactionRepository.save(transactionView);
    }

    @EventHandler
    public void on(AccountDebited event) {
        Optional<TransactionProjection> optional = transactionRepository.findById(event.getTransactionId());
        if (optional.isPresent()) {
            TransactionProjection transactionView = optional.get();
            transactionView.setTransactionStatus(TransactionStatus.IN_PROGRESS.toString());
            transactionView.setUpdatedAt(event.getUpdatedAt());
            transactionRepository.save(transactionView);
        }
    }

    @EventHandler
    public void on(AccountCredited event) {
        Optional<TransactionProjection> optional = transactionRepository.findById(event.getTransactionId());
        if (optional.isPresent()) {
            TransactionProjection transactionView = optional.get();
            transactionView.setTransactionStatus(TransactionStatus.IN_PROGRESS.toString());
            transactionView.setUpdatedAt(event.getUpdatedAt());
            transactionRepository.save(transactionView);
        }
    }

    @EventHandler
    public void on(DepositCompleted event) {
        Optional<TransactionProjection> optional = transactionRepository.findById(event.getTransactionId());
        if (optional.isPresent()) {
            TransactionProjection transactionView = optional.get();
            transactionView.setTransactionStatus(TransactionStatus.COMPLETED.toString());
            transactionView.setUpdatedAt(event.getUpdatedAt());
            transactionRepository.save(transactionView);
        }
    }

    @EventHandler
    public void on(WithdrawCompleted event) {
        Optional<TransactionProjection> optional = transactionRepository.findById(event.getTransactionId());
        if (optional.isPresent()) {
            TransactionProjection transactionView = optional.get();
            transactionView.setTransactionStatus(TransactionStatus.COMPLETED.toString());
            transactionView.setUpdatedAt(event.getUpdatedAt());
            transactionRepository.save(transactionView);
        }
    }

    @EventHandler
    public void on(TransferCompleted event) {
        Optional<TransactionProjection> optional = transactionRepository.findById(event.getTransactionId());
        if (optional.isPresent()) {
            TransactionProjection view = optional.get();
            view.setTransactionStatus(TransactionStatus.COMPLETED.toString());
            view.setUpdatedAt(event.getUpdatedAt());
            transactionRepository.save(view);
        }
    }

    @EventHandler
    public void on(DepositFailed event) {
        Optional<TransactionProjection> optional = transactionRepository.findById(event.getTransactionId());
        if (optional.isPresent()) {
            TransactionProjection transactionView = optional.get();
            transactionView.setTransactionStatus(TransactionStatus.FAILED.toString());
            transactionView.setUpdatedAt(event.getUpdatedAt());
            transactionRepository.save(transactionView);
        }
    }

    @EventHandler
    public void on(WithdrawFailed event) {
        Optional<TransactionProjection> optional = transactionRepository.findById(event.getTransactionId());
        if (optional.isPresent()) {
            TransactionProjection transactionView = optional.get();
            transactionView.setTransactionStatus(TransactionStatus.FAILED.toString());
            transactionView.setUpdatedAt(event.getUpdatedAt());
            transactionRepository.save(transactionView);
        }
    }

    @EventHandler
    public void on(TransferFailed event) {
        Optional<TransactionProjection> optional = transactionRepository.findById(event.getTransactionId());
        if (optional.isPresent()) {
            TransactionProjection transactionView = optional.get();
            transactionView.setTransactionStatus(TransactionStatus.FAILED.toString());
            transactionView.setUpdatedAt(event.getUpdatedAt());
            transactionRepository.save(transactionView);
        }
    }
}
