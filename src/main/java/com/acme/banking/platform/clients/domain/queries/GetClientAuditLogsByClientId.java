package com.acme.banking.platform.clients.domain.queries;

import lombok.Value;

@Value
public class GetClientAuditLogsByClientId {
    private final Long id;

    public GetClientAuditLogsByClientId(Long id) {
        this.id = id;
    }
}
