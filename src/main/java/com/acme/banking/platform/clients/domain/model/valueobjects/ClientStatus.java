package com.acme.banking.platform.clients.domain.model.valueobjects;

public enum ClientStatus {
    ACTIVE (1),
    INACTIVE(0);

    private int id;

    ClientStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
