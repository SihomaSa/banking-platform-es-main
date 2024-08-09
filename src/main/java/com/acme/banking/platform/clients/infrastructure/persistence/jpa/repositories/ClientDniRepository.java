package com.acme.banking.platform.clients.infrastructure.persistence.jpa.repositories;

import com.acme.banking.platform.clients.domain.processors.ClientDni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface ClientDniRepository extends JpaRepository<ClientDni, Long> {
    Optional<ClientDni> getByDni(String dni);

    @Query(value = "SELECT * FROM client_dnis WHERE id <> :id AND dni = :dni LIMIT 1", nativeQuery = true)
    Optional<ClientDni> getByDniForDistinctId(String dni, Long id);
}
