package com.acme.banking.platform.transactions.interfaces.rest.transform;

import com.acme.banking.platform.transactions.domain.commands.DepositMoney;
import com.acme.banking.platform.transactions.interfaces.rest.resources.DepositMoneyResource;

public class DepositMoneyCommandFromResourceAssembler {
    public static DepositMoney toCommandFromResource(DepositMoneyResource resource) {
        return new DepositMoney(
            resource.transactionId(),
            resource.accountId(),
            resource.amount(),
            resource.createdAt());
    }
}
