package com.acme.banking.platform.accounts.domain.queries;

import lombok.Value;

@Value
public class GetAccountByNumber {
    private final String number;

    public GetAccountByNumber(String number) {
        this.number = number;
    }
}
