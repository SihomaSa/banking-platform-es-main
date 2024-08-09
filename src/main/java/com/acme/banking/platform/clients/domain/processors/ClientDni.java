package com.acme.banking.platform.clients.domain.processors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ClientDni {
    @Id
    public Long id;

    @Column(length=8, unique = true)
    public String dni;

    public ClientDni() {
    }

    public ClientDni(Long id, String dni) {
        this.id = id;
        this.dni = dni;
    }
}
