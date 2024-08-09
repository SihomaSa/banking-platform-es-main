package com.acme.banking.platform.clients.domain.queries;

import lombok.Value;

@Value
public class GetClients {
    private final Integer page;
    private final Integer limit;

    public GetClients(Integer page, Integer limit) {
        page = page <= 0 ? 1 : page;
        limit = (limit <= 0 || limit > 100) ? 100 : limit;
        this.page = page;
        this.limit = limit;
    }
}
