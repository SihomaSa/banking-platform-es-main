package com.acme.banking.platform.transactions.interfaces.rest.transform;

import com.acme.banking.platform.transactions.domain.commands.TransferMoney;
import com.acme.banking.platform.transactions.interfaces.rest.resources.TransferMoneyResource;

public class TransferMoneyResourceFromCommandAssembler {
    public static TransferMoneyResource toResourceFromTransferMoney(TransferMoney command) {
        return new TransferMoneyResource(
            command.getTransactionId(),
            command.getFromAccountId(),
            command.getToAccountId(),
            command.getAmount(),
            command.getCreatedAt());
    }
}
