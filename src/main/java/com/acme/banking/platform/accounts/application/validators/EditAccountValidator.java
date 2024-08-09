package com.acme.banking.platform.accounts.application.validators;

import com.acme.banking.platform.accounts.domain.commands.EditAccount;
import com.acme.banking.platform.accounts.domain.processors.AccountNumber;
import com.acme.banking.platform.accounts.infrastructure.persistence.jpa.repositories.AccountNumberRepository;
import com.acme.banking.platform.shared.domain.model.valueobjects.Notification;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class EditAccountValidator {
    private final AccountNumberRepository accountNumberRepository;

    public EditAccountValidator(AccountNumberRepository accountNumberRepository) {
        this.accountNumberRepository = accountNumberRepository;
    }

    public Notification validate(EditAccount command)
    {
        Notification notification = new Notification();
        Long id = command.getId();
        if (id <= 0) {
            notification.addError("Account id must be greater than zero");
            return notification;
        }
        Optional<AccountNumber> optional = accountNumberRepository.findById(id);
        if (optional.isEmpty()) {
            notification.addError("Account not found");
            return notification;
        }
        return notification;
    }
}
