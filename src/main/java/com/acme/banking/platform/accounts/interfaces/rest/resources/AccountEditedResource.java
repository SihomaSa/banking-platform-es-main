package com.acme.banking.platform.accounts.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountEditedResource(
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id,
    BigDecimal overdraftLimit,
    LocalDateTime updatedAt
) {}
