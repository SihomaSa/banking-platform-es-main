package com.acme.banking.platform.clients.interfaces.eventhandlers;

import com.acme.banking.platform.clients.domain.events.ClientEdited;
import com.acme.banking.platform.clients.domain.events.ClientRegistered;
import com.acme.banking.platform.clients.domain.model.valueobjects.ClientStatus;
import com.acme.banking.platform.clients.domain.projections.ClientAuditLogProjection;
import com.acme.banking.platform.clients.infrastructure.persistence.jpa.repositories.ClientAuditLogRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ClientAuditLogProjectionsEventHandler {
    private final ClientAuditLogRepository clientAuditRepository;

    public ClientAuditLogProjectionsEventHandler(ClientAuditLogRepository clientAuditRepository) {
        this.clientAuditRepository = clientAuditRepository;
    }

    @EventHandler
    public void on(ClientRegistered event) {
        ClientAuditLogProjection view = new ClientAuditLogProjection(
            event.getId(),
            event.getFirstName(),
            event.getLastName(),
            event.getDni(),
            ClientStatus.ACTIVE.name(),
            event.getCreatedAt());
        clientAuditRepository.save(view);
    }

    @EventHandler
    public void on(ClientEdited event) {
        Optional<ClientAuditLogProjection> viewOptional = clientAuditRepository.getLastByClientId(event.getId());
        if (viewOptional.isPresent()) {
            ClientAuditLogProjection lastClientAudit = viewOptional.get();
            ClientAuditLogProjection clientAudit = new ClientAuditLogProjection(lastClientAudit);
            clientAudit.setFirstName(event.getFirstName());
            clientAudit.setLastName(event.getLastName());
            clientAudit.setDni(event.getDni());
            clientAudit.setCreatedAt(event.getUpdatedAt());
            clientAuditRepository.save(clientAudit);
        }
    }
}
