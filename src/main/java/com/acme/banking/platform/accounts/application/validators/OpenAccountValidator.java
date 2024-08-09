package com.acme.banking.platform.accounts.application.validators;

import com.acme.banking.platform.accounts.domain.commands.OpenAccount;
import com.acme.banking.platform.accounts.domain.processors.AccountNumber;
import com.acme.banking.platform.accounts.infrastructure.persistence.jpa.repositories.AccountNumberRepository;
import com.acme.banking.platform.shared.domain.model.valueobjects.Notification;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Optional;

@Component
public class OpenAccountValidator {
    private final AccountNumberRepository accountNumberRepository;
    private final static int NUMBER_MAX_LENGTH = 25;

    public OpenAccountValidator(AccountNumberRepository accountNumberRepository) {
        this.accountNumberRepository = accountNumberRepository;
    }

    public Notification validate(OpenAccount command)
    {
        Notification notification = new Notification();

        String number = command.getNumber().trim();
        if (number.isEmpty()) notification.addError("Account number is required");

        if (number.length() > NUMBER_MAX_LENGTH) notification.addError("Account number must be less than " + NUMBER_MAX_LENGTH + " characters");

        Long clientId = command.getClientId();
        if (clientId <= 0) notification.addError("Account clientId is required");

        BigDecimal overdraftLimit = command.getOverdraftLimit();
        if (overdraftLimit.doubleValue() < 0) notification.addError("Account overdraftLimit must be greater or equal than zero");

        if (notification.hasErrors()) return notification;

        Optional<AccountNumber> optional = accountNumberRepository.getByNumber(number);
        if (optional.isPresent()) notification.addError("Account number is taken");

        return notification;
    }
}
