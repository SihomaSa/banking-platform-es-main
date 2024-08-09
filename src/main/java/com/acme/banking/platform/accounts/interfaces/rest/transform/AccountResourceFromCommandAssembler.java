package com.acme.banking.platform.accounts.interfaces.rest.transform;

import com.acme.banking.platform.accounts.domain.commands.EditAccount;
import com.acme.banking.platform.accounts.domain.commands.OpenAccount;
import com.acme.banking.platform.accounts.interfaces.rest.resources.AccountEditedResource;
import com.acme.banking.platform.accounts.interfaces.rest.resources.AccountResource;
import java.math.BigDecimal;

public class AccountResourceFromCommandAssembler {
    public static AccountResource toResourceFromOpenAccount(OpenAccount command) {
        return new AccountResource(
            command.getId(),
            command.getNumber(),
            BigDecimal.ZERO,
            command.getOverdraftLimit(),
            command.getClientId());
    }

    public static AccountEditedResource toResourceFromEditClient(EditAccount command) {
        return new AccountEditedResource(
                command.getId(),
                command.getOverdraftLimit(),
                command.getUpdatedAt());
    }
}
