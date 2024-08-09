package com.acme.banking.platform.transactions.domain.model.aggregates;

public enum TransactionStatus {
    FAILED(0),
    STARTED(1),
    IN_PROGRESS(2),
    COMPLETED(3);

    private int id;

    TransactionStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
