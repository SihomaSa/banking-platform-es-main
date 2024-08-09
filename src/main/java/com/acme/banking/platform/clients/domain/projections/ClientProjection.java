package com.acme.banking.platform.clients.domain.projections;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ClientProjection {
    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(length=50)
    private String firstName;

    @Column(length=50)
    private String lastName;

    @Column(length=8, unique=true)
    private String dni;

    @Column(length=8)
    private String status;

    @Column
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    public ClientProjection() {
    }

    public ClientProjection(Long id, String firstName, String lastName, String dni, String status, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
