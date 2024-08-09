package com.acme.banking.platform.transactions.application.command.validators;

import com.acme.banking.platform.shared.domain.model.valueobjects.Notification;
import com.acme.banking.platform.transactions.domain.commands.DepositMoney;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DepositValidator {
    public DepositValidator() {
    }

    public Notification validate(DepositMoney command)
    {
        Notification notification = new Notification();

        Long transactionId = command.getTransactionId();
        if (transactionId <= 0) notification.addError("transactionId must be greater than zero");

        Long accountId = command.getAccountId();
        if (accountId <= 0) notification.addError("accountId must be greater than zero");

        BigDecimal amount = command.getAmount();
        if (amount.doubleValue() <= 0) notification.addError("amount must be greater than zero");

        return notification;
    }
}
