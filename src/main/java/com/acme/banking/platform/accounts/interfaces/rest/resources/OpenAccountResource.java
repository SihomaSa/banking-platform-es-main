package com.acme.banking.platform.accounts.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OpenAccountResource(
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id,

    String number,
    BigDecimal overdraftLimit,
    Long clientId,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDateTime createdAt
) {
    public OpenAccountResource withId(Long id) {
        LocalDateTime createdAt = LocalDateTime.now();
        return new OpenAccountResource(id, this.number, this.overdraftLimit, this.clientId, createdAt);
    }
}
