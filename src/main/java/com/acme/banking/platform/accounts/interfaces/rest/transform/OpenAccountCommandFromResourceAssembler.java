package com.acme.banking.platform.accounts.interfaces.rest.transform;

import com.acme.banking.platform.accounts.domain.commands.OpenAccount;
import com.acme.banking.platform.accounts.interfaces.rest.resources.OpenAccountResource;

public class OpenAccountCommandFromResourceAssembler {
    public static OpenAccount toCommandFromResource(OpenAccountResource resource) {
        return new OpenAccount(
            resource.id(),
            resource.number(),
            resource.overdraftLimit(),
            resource.clientId(),
            resource.createdAt());
    }
}
