package com.acme.banking.platform.transactions.domain.projections;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class TransactionProjection {
    @Id
    @Column
    private Long id;

    @Column
    private Long fromAccountId;

    @Column(nullable = true)
    private Long toAccountId;

    @Column
    private BigDecimal amount;

    @Column(length=15)
    private String transactionType;

    @Column(length=15)
    private String transactionStatus;

    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    public TransactionProjection() {
    }

    public TransactionProjection(Long id, Long fromAccountId, Long toAccountId, BigDecimal amount, String transactionType, String transactionStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
