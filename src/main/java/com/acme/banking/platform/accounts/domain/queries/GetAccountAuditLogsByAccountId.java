package com.acme.banking.platform.accounts.domain.queries;

import lombok.Value;

@Value
public class GetAccountAuditLogsByAccountId {
    private final Long id;

    public GetAccountAuditLogsByAccountId(Long id) {
        this.id = id;
    }
}
