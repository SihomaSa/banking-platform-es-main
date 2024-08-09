package com.acme.banking.platform.clients.interfaces.rest.resources;

import com.acme.banking.platform.clients.domain.projections.ClientProjection;
import com.acme.banking.platform.shared.domain.model.valueobjects.Error;
import java.util.List;

public record GetClientsResponseResource(
    List<ClientProjection> success,
    List<Error> errors
) {}
