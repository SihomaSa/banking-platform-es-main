package com.acme.banking.platform.accounts.infrastructure.persistence.jpa.repositories;

import com.acme.banking.platform.accounts.domain.projections.AccountAuditLogProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountAuditLogRepository extends JpaRepository<AccountAuditLogProjection, Long> {
    AccountAuditLogProjection findOneByAccountId(Long accountId);

    @Query(value = "SELECT * FROM account_audit_log_projections WHERE account_id = :accountId ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<AccountAuditLogProjection> getLastByAccountId(Long accountId);

    @Query(value = "SELECT * FROM account_audit_log_projections WHERE account_id = :accountId ORDER BY created_at", nativeQuery = true)
    List<AccountAuditLogProjection> getByAccountId(Long accountId);
}
