package com.acme.banking.platform.clients.domain.projections;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ClientAuditLogProjection {
    @Id
    @GeneratedValue
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long clientId;

    @Column(length=50)
    private String firstName;

    @Column(length=50)
    private String lastName;

    @Column(length=8)
    private String dni;

    @Column(length=8)
    private String status;

    @Column
    private LocalDateTime createdAt;

    public ClientAuditLogProjection() {
    }

    public ClientAuditLogProjection(Long clientId, String firstName, String lastName, String dni, String status, LocalDateTime createdAt) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.status = status;
        this.createdAt = createdAt;
    }

    public ClientAuditLogProjection(ClientAuditLogProjection view) {
        this.clientId = view.getClientId();
        this.firstName = view.getFirstName();
        this.lastName = view.getLastName();
        this.dni = view.getDni();
        this.status = view.getStatus();
        this.createdAt = view.getCreatedAt();
    }
}
