package com.acme.banking.platform.clients.application.queryhandlers;

import com.acme.banking.platform.clients.domain.projections.ClientProjection;
import com.acme.banking.platform.clients.domain.projections.ClientAuditLogProjection;
import com.acme.banking.platform.clients.domain.queries.GetClientAuditLogsByClientId;
import com.acme.banking.platform.clients.domain.queries.GetClients;
import com.acme.banking.platform.clients.infrastructure.persistence.jpa.repositories.ClientAuditLogRepository;
import com.acme.banking.platform.clients.infrastructure.persistence.jpa.repositories.ClientRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ClientAggregateQueryHandler {
    private final ClientRepository clientRepository;
    private final ClientAuditLogRepository clientAuditRepository;

    public ClientAggregateQueryHandler(ClientRepository clientRepository, ClientAuditLogRepository clientAuditRepository) {
        this.clientRepository = clientRepository;
        this.clientAuditRepository = clientAuditRepository;
    }

    @QueryHandler
    public List<ClientAuditLogProjection> handle(GetClientAuditLogsByClientId query) {
        return this.clientAuditRepository.getByClientId(query.getId());
    }

    @QueryHandler
    public List<ClientProjection> handle(GetClients query) {
        return this.clientRepository.getPaginated(query.getPage(), query.getLimit());
    }
}
