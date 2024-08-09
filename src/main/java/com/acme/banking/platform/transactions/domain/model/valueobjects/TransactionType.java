package com.acme.banking.platform.transactions.domain.model.valueobjects;

public enum TransactionType {
    DEPOSIT(1), WITHDRAWAL(2), TRANSFER(3);

    private int id;

    TransactionType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
