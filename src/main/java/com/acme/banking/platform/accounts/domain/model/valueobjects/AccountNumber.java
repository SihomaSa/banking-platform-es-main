package com.acme.banking.platform.accounts.domain.model.valueobjects;

import com.acme.banking.platform.shared.domain.model.valueobjects.Notification;
import com.acme.banking.platform.shared.domain.model.valueobjects.Result;

public record AccountNumber(String value) {
    private static final int MAX_LENGTH = 25;

    public static Result<AccountNumber, Notification> create(String value) {
        Notification notification = new Notification();
        value = value.trim();
        if (value.isEmpty()) notification.addError("account number is required");
        if (value.length() > MAX_LENGTH) notification.addError("The maximum length of an account number is " + MAX_LENGTH);
        if (notification.hasErrors()) return Result.failure(notification);
        return Result.success(new AccountNumber(value));
    }
}
