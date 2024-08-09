package com.acme.banking.platform.clients.interfaces.rest.resources;

import com.acme.banking.platform.clients.domain.projections.ClientAuditLogProjection;
import com.acme.banking.platform.shared.domain.model.valueobjects.Error;
import java.util.List;

public record ClientAuditLogResponseResource(
    List<ClientAuditLogProjection> success,
    List<Error> errors
) {}
