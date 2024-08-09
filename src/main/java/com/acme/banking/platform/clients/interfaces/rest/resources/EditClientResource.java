package com.acme.banking.platform.clients.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record EditClientResource (
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id,

    String firstName,
    String lastName,
    String dni,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDateTime updatedAt
) {
    public EditClientResource withId(Long id) {
        LocalDateTime updatedAt = LocalDateTime.now();
        return new EditClientResource(id, this.firstName, this.lastName, this.dni, updatedAt);
    }
}
