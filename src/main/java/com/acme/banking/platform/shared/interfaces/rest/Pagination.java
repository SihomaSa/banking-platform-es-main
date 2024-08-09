package com.acme.banking.platform.shared.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;

public class Pagination {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static HttpHeaders createPaginationHeaders(int totalItems, int page, int limit) {
        page = page <= 0 ? 1 : page;
        limit = (limit <= 0 || limit > 100) ? 100 : limit;
        PaginationMetadata paginationMetadata = new PaginationMetadata(totalItems, page, limit);
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.set("X-Pagination", objectMapper.writeValueAsString(paginationMetadata));
        } catch (Exception e) {
            throw new RuntimeException("Error creating pagination headers", e);
        }
        return headers;
    }
}
