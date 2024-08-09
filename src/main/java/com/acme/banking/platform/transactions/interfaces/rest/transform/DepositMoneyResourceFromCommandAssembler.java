package com.acme.banking.platform.transactions.interfaces.rest.transform;

import com.acme.banking.platform.transactions.domain.commands.DepositMoney;
import com.acme.banking.platform.transactions.interfaces.rest.resources.DepositMoneyResource;

public class DepositMoneyResourceFromCommandAssembler {
    public static DepositMoneyResource toResourceFromDepositMoney(DepositMoney command) {
        return new DepositMoneyResource(
            command.getTransactionId(),
            command.getAccountId(),
            command.getAmount(),
            command.getCreatedAt());
    }
}
