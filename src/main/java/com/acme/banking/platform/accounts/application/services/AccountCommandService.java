package com.acme.banking.platform.accounts.application.services;

import com.acme.banking.platform.accounts.application.validators.EditAccountValidator;
import com.acme.banking.platform.accounts.application.validators.OpenAccountValidator;
import com.acme.banking.platform.accounts.domain.commands.EditAccount;
import com.acme.banking.platform.accounts.domain.commands.OpenAccount;
import com.acme.banking.platform.shared.domain.model.valueobjects.Notification;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

@Component
public class AccountCommandService {
    private final CommandGateway commandGateway;
    private final OpenAccountValidator openAccountValidator;
    private final EditAccountValidator editAccountValidator;

    public AccountCommandService(CommandGateway commandGateway, OpenAccountValidator openAccountValidator, EditAccountValidator editAccountValidator) {
        this.commandGateway = commandGateway;
        this.openAccountValidator = openAccountValidator;
        this.editAccountValidator = editAccountValidator;
    }

    public Notification open(OpenAccount command) throws Exception {
        Notification notification = this.openAccountValidator.validate(command);
        if (notification.hasErrors()) return notification;
        commandGateway.sendAndWait(command);
        return notification;
    }

    public Notification edit(EditAccount command) throws Exception {
        Notification notification = this.editAccountValidator.validate(command);
        if (notification.hasErrors()) return notification;
        commandGateway.sendAndWait(command);
        return notification;
    }
}
