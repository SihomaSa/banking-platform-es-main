package com.acme.banking.platform.clients.infrastructure.persistence.jpa.repositories;

import com.acme.banking.platform.clients.domain.projections.ClientProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientProjection, Long> {
    @Procedure(procedureName = "get_clients")
    List<ClientProjection> getPaginated(long page, long limit);

    Optional<ClientProjection> getByDni(String dni);

    @Query(value = "SELECT * FROM client_projections WHERE id <> :id AND dni = :dni", nativeQuery = true)
    Optional<ClientProjection> getByIdAndDni(Long id, String dni);
}
