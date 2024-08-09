package com.acme.banking.platform.transactions.interfaces.rest.resources;

import com.acme.banking.platform.shared.domain.model.valueobjects.Error;
import java.util.List;

public record TransferMoneyResponseResource(
        TransferMoneyResource success,
        List<Error> errors
) {}
