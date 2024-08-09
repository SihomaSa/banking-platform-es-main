package com.acme.banking.platform.transactions.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DepositMoneyResource(
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long transactionId,

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long accountId,
    BigDecimal amount,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDateTime createdAt
) {
    public DepositMoneyResource withTransactionId(Long transactionId) {
        LocalDateTime createdAt = LocalDateTime.now();
        return new DepositMoneyResource(transactionId, this.accountId, this.amount, createdAt);
    }
}
