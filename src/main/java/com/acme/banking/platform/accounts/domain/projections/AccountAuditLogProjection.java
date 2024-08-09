package com.acme.banking.platform.accounts.domain.projections;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AccountAuditLogProjection {
    @Id
    @GeneratedValue
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountId;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long clientId;

    @Column
    private BigDecimal balance;

    @Column
    private BigDecimal overdraftLimit;

    @Column
    private LocalDateTime createdAt;

    public AccountAuditLogProjection() {
    }

    public AccountAuditLogProjection(Long accountId, Long clientId, BigDecimal balance, BigDecimal overdraftLimit, LocalDateTime createdAt) {
        this.accountId = accountId;
        this.clientId = clientId;
        this.balance = balance;
        this.overdraftLimit = overdraftLimit;
        this.createdAt = createdAt;
    }

    public AccountAuditLogProjection(AccountAuditLogProjection view) {
        this.accountId = view.getAccountId();
        this.clientId = view.getClientId();
        this.balance = view.getBalance();
        this.overdraftLimit = view.getOverdraftLimit();
        this.createdAt = view.getCreatedAt();
    }
}
