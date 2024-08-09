package com.acme.banking.platform.accounts.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

public record AccountResource(
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id,
    String number,
    BigDecimal balance,
    BigDecimal overdraftLimit,
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long clientId
) {}
