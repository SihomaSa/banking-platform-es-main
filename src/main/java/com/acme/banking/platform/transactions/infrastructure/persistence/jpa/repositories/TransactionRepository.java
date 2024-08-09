package com.acme.banking.platform.transactions.infrastructure.persistence.jpa.repositories;

import com.acme.banking.platform.transactions.domain.projections.TransactionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionProjection, Long> {
}
