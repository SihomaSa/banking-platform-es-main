package com.acme.banking.platform.accounts.interfaces.rest.transform;

import com.acme.banking.platform.accounts.domain.commands.EditAccount;
import com.acme.banking.platform.accounts.interfaces.rest.resources.EditAccountResource;

public class EditAccountCommandFromResourceAssembler {
    public static EditAccount toCommandFromResource(EditAccountResource resource) {
        return new EditAccount(
            resource.id(),
            resource.overdraftLimit(),
            resource.updatedAt());
    }
}
