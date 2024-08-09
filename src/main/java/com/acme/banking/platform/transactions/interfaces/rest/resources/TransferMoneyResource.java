package com.acme.banking.platform.transactions.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferMoneyResource(
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long transactionId,

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long fromAccountId,

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long toAccountId,

    BigDecimal amount,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDateTime createdAt
) {
    public TransferMoneyResource withTransactionId(Long transactionId) {
        LocalDateTime createdAt = LocalDateTime.now();
        return new TransferMoneyResource(transactionId, this.fromAccountId, this.toAccountId, this.amount, createdAt);
    }

    public TransferMoneyResource withAccountIds(Long fromAccountId, Long toAccountId) {
        LocalDateTime createdAt = LocalDateTime.now();
        return new TransferMoneyResource(this.transactionId, fromAccountId, toAccountId, this.amount, createdAt);
    }
}
