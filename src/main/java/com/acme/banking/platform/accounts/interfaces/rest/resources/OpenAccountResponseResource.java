package com.acme.banking.platform.accounts.interfaces.rest.resources;

import com.acme.banking.platform.shared.domain.model.valueobjects.Error;
import java.util.List;

public record OpenAccountResponseResource(
        AccountResource success,
        List<Error> errors
) {}
