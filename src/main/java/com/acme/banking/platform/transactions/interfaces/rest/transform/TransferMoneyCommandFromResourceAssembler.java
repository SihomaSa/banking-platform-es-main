package com.acme.banking.platform.transactions.interfaces.rest.transform;

import com.acme.banking.platform.transactions.domain.commands.TransferMoney;
import com.acme.banking.platform.transactions.interfaces.rest.resources.TransferMoneyResource;

public class TransferMoneyCommandFromResourceAssembler {
    public static TransferMoney toCommandFromResource(TransferMoneyResource resource) {
        return new TransferMoney(
            resource.transactionId(),
            resource.fromAccountId(),
            resource.toAccountId(),
            resource.amount(),
            resource.createdAt()
        );
    }
}
