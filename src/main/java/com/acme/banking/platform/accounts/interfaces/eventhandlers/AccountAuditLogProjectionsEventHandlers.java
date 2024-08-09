package com.acme.banking.platform.accounts.interfaces.eventhandlers;

import com.acme.banking.platform.accounts.domain.events.*;
import com.acme.banking.platform.accounts.domain.projections.AccountAuditLogProjection;
import com.acme.banking.platform.accounts.infrastructure.persistence.jpa.repositories.AccountAuditLogRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Optional;

@Component
public class AccountAuditLogProjectionsEventHandlers {
    private final AccountAuditLogRepository accountAuditLogRepository;

    public AccountAuditLogProjectionsEventHandlers(AccountAuditLogRepository accountAuditLogRepository) {
        this.accountAuditLogRepository = accountAuditLogRepository;
    }

    @EventHandler
    public void on(AccountOpened event) {
        AccountAuditLogProjection accountAuditLog = new AccountAuditLogProjection(
            event.getId(),
            event.getClientId(),
            BigDecimal.ZERO,
            event.getOverdraftLimit(),
            event.getCreatedAt());
        accountAuditLogRepository.save(accountAuditLog);
    }

    @EventHandler
    public void on(AccountEdited event) {
        Optional<AccountAuditLogProjection> optional = accountAuditLogRepository.getLastByAccountId(event.getId());
        if (optional.isPresent()) {
            AccountAuditLogProjection view = optional.get();
            view = new AccountAuditLogProjection(view);
            view.setOverdraftLimit(event.getOverdraftLimit());
            view.setCreatedAt(event.getUpdatedAt());
            accountAuditLogRepository.save(view);
        }
    }

    @EventHandler
    public void on(AccountDebited event) {
        Optional<AccountAuditLogProjection> optional = accountAuditLogRepository.getLastByAccountId(event.getAccountId());
        if (optional.isPresent()) {
            AccountAuditLogProjection view = optional.get();
            view = new AccountAuditLogProjection(view);
            view.setBalance(view.getBalance().subtract(event.getAmount()));
            view.setCreatedAt(event.getUpdatedAt());
            accountAuditLogRepository.save(view);
        }
    }

    @EventHandler
    public void on(AccountCredited event) {
        Optional<AccountAuditLogProjection> optional = accountAuditLogRepository.getLastByAccountId(event.getAccountId());
        if (optional.isPresent()) {
            AccountAuditLogProjection view = optional.get();
            view = new AccountAuditLogProjection(view);
            view.setBalance(view.getBalance().add(event.getAmount()));
            view.setCreatedAt(event.getUpdatedAt());
            accountAuditLogRepository.save(view);
        }
    }

    @EventHandler
    public void on(FromAccountCredited event) {
        Optional<AccountAuditLogProjection> optional = accountAuditLogRepository.getLastByAccountId(event.getAccountId());
        if (optional.isPresent()) {
            AccountAuditLogProjection view = optional.get();
            view = new AccountAuditLogProjection(view);
            view.setBalance(view.getBalance().add(event.getAmount()));
            view.setCreatedAt(event.getUpdatedAt());
            accountAuditLogRepository.save(view);
        }
    }
}
