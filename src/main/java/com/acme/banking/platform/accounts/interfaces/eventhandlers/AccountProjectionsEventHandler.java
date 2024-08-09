package com.acme.banking.platform.accounts.interfaces.eventhandlers;

import com.acme.banking.platform.accounts.domain.events.*;
import com.acme.banking.platform.accounts.domain.projections.AccountProjection;
import com.acme.banking.platform.accounts.infrastructure.persistence.jpa.repositories.AccountRepository;
import com.acme.banking.platform.clients.domain.projections.ClientProjection;
import com.acme.banking.platform.clients.infrastructure.persistence.jpa.repositories.ClientRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Optional;

@Component
public class AccountProjectionsEventHandler {
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountProjectionsEventHandler(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @EventHandler
    public void on(AccountOpened event) {
        Optional<ClientProjection> client = clientRepository.findById(event.getClientId());
        String clientDni = client.isPresent() ? client.get().getDni() : null;
        String clientName = client.isPresent() ? client.get().getFullName() : null;
        AccountProjection account = new AccountProjection(
            event.getId(),
            event.getClientId(),
            clientDni,
            clientName,
            event.getNumber(),
            BigDecimal.ZERO,
            event.getOverdraftLimit(),
            event.getCreatedAt());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountEdited event) {
        Optional<AccountProjection> optional = accountRepository.findById(event.getId());
        if (optional.isPresent()) {
            AccountProjection accountView = optional.get();
            accountView.setOverdraftLimit(event.getOverdraftLimit());
            accountView.setUpdatedAt(event.getUpdatedAt());
            accountRepository.save(accountView);
        }
    }

    @EventHandler
    public void on(AccountDebited event) {
        Optional<AccountProjection> optional = accountRepository.findById(event.getAccountId());
        if (optional.isPresent()) {
            AccountProjection view = optional.get();
            view.setBalance(view.getBalance().subtract(event.getAmount()));
            view.setUpdatedAt(event.getUpdatedAt());
            accountRepository.save(view);
        }
    }

    @EventHandler
    public void on(AccountCredited event) {
        Optional<AccountProjection> optional = accountRepository.findById(event.getAccountId());
        if (optional.isPresent()) {
            AccountProjection view = optional.get();
            view.setBalance(view.getBalance().add(event.getAmount()));
            view.setUpdatedAt(event.getUpdatedAt());
            accountRepository.save(view);
        }
    }

    @EventHandler
    public void on(FromAccountCredited event) {
        Optional<AccountProjection> optional = accountRepository.findById(event.getAccountId());
        if (optional.isPresent()) {
            AccountProjection view = optional.get();
            view.setBalance(view.getBalance().add(event.getAmount()));
            view.setUpdatedAt(event.getUpdatedAt());
            accountRepository.save(view);
        }
    }
}
