package com.acme.banking.platform.transactions.interfaces.rest.transform;

import com.acme.banking.platform.transactions.domain.commands.WithdrawMoney;
import com.acme.banking.platform.transactions.interfaces.rest.resources.WithdrawMoneyResource;

public class WithdrawMoneyResourceFromCommandAssembler {
    public static WithdrawMoneyResource toResourceFromDepositMoney(WithdrawMoney command) {
        return new WithdrawMoneyResource(
            command.getTransactionId(),
            command.getAccountId(),
            command.getAmount(),
            command.getCreatedAt());
    }
}
