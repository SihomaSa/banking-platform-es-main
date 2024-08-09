package com.acme.banking.platform.accounts.interfaces.rest.resources;

import com.acme.banking.platform.accounts.domain.projections.AccountProjection;
import com.acme.banking.platform.shared.domain.model.valueobjects.Error;

import java.util.List;

public record GetAccountsResponseResource(
    List<AccountProjection> success,
    List<Error> errors
) {}
