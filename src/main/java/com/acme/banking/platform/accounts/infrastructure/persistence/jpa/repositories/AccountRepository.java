package com.acme.banking.platform.accounts.infrastructure.persistence.jpa.repositories;

import com.acme.banking.platform.accounts.domain.projections.AccountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountProjection, Long> {
    @Procedure(procedureName = "get_accounts")
    List<AccountProjection> getAccounts(Integer page, Integer limit);

    Optional<AccountProjection> getByNumber(String number);
}
