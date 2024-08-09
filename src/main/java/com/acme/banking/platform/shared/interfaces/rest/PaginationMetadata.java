package com.acme.banking.platform.shared.interfaces.rest;

import lombok.Value;

@Value
public class PaginationMetadata {
    private final Integer totalItemCount;
    private final Integer totalPageCount;
    private final Integer page;
    private final Integer limit;

    public PaginationMetadata(Integer totalItemCount, Integer page, Integer limit)
    {
        this.totalItemCount = totalItemCount;
        this.page = page;
        this.limit = limit;
        this.totalPageCount = (int) Math.ceil((double) totalItemCount / limit);
    }
}
