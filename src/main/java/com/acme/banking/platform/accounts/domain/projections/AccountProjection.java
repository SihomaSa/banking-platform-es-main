package com.acme.banking.platform.accounts.domain.projections;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
    indexes = {
        @Index(name = "ix_accounts_client_dni", columnList = "clientDni")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "ix_accounts_number", columnNames = "number")
    }
)
public class AccountProjection {
    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long clientId;

    @Column(length=8)
    private String clientDni;

    @Column
    private String clientName;

    @Column(length=25)
    private String number;

    @Column
    private BigDecimal balance;

    @Column
    private BigDecimal overdraftLimit;

    @Column
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    public AccountProjection() {
    }

    public AccountProjection(Long id, Long clientId, String clientDni, String clientName, String number, BigDecimal balance, BigDecimal overdraftLimit, LocalDateTime createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.clientDni = clientDni;
        this.clientName = clientName;
        this.number = number;
        this.balance = balance;
        this.overdraftLimit = overdraftLimit;
        this.createdAt = createdAt;
    }
}
