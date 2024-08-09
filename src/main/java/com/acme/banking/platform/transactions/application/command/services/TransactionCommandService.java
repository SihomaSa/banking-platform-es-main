package com.acme.banking.platform.transactions.application.command.services;

import com.acme.banking.platform.shared.domain.model.valueobjects.Notification;
import com.acme.banking.platform.transactions.application.command.validators.DepositValidator;
import com.acme.banking.platform.transactions.application.command.validators.TransferValidator;
import com.acme.banking.platform.transactions.application.command.validators.WithdrawValidator;
import com.acme.banking.platform.transactions.domain.commands.DepositMoney;
import com.acme.banking.platform.transactions.domain.commands.TransferMoney;
import com.acme.banking.platform.transactions.domain.commands.WithdrawMoney;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

@Component
public class TransactionCommandService {
    private final CommandGateway commandGateway;
    private final DepositValidator depositValidator;
    private final WithdrawValidator withdrawValidator;
    private final TransferValidator transferValidator;

    public TransactionCommandService(
            CommandGateway commandGateway,
            DepositValidator depositValidator,
            WithdrawValidator withdrawValidator,
            TransferValidator transferValidator) {
        this.commandGateway = commandGateway;
        this.depositValidator = depositValidator;
        this.withdrawValidator = withdrawValidator;
        this.transferValidator = transferValidator;
    }

    public Notification deposit(DepositMoney command) throws Exception {
        Notification notification = this.depositValidator.validate(command);
        if (notification.hasErrors()) return notification;
        commandGateway.sendAndWait(command);
        return notification;
    }

    public Notification withdraw(WithdrawMoney command) throws Exception {
        Notification notification = this.withdrawValidator.validate(command);
        if (notification.hasErrors()) return notification;
        commandGateway.sendAndWait(command);
        return notification;
    }

    public Notification transfer(TransferMoney command) throws Exception {
        Notification notification = this.transferValidator.validate(command);
        if (notification.hasErrors()) return notification;
        commandGateway.sendAndWait(command);
        return notification;
    }
}
