package com.acme.banking.platform.transactions.application.command.validators;

import com.acme.banking.platform.shared.domain.model.valueobjects.Notification;
import com.acme.banking.platform.transactions.domain.commands.TransferMoney;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class TransferValidator {
    public TransferValidator() {
    }

    public Notification validate(TransferMoney command)
    {
        Notification notification = new Notification();

        Long transactionId = command.getTransactionId();
        if (transactionId <= 0) notification.addError("transactionId must be greater than zero");

        Long fromAccountId = command.getFromAccountId();
        if (fromAccountId <= 0) notification.addError("fromAccountId must be greater than zero");

        Long toAccountId = command.getToAccountId();
        if (toAccountId <= 0) notification.addError("toAccountId must be greater than zero");

        BigDecimal amount = command.getAmount();
        if (amount.doubleValue() <= 0) notification.addError("Amount must be greater than zero");

        return notification;
    }
}
