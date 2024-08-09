package com.acme.banking.platform.shared.domain.model.valueobjects;

public record Dni(String value) {
    private static final int MAX_LENGTH = 8;

    public static Result<Dni, Notification> create(String value) {
        Notification notification = new Notification();
        value = value.trim();
        if (value.isEmpty()) notification.addError("dni is required");
        if (value.length() > MAX_LENGTH) notification.addError("The maximum length of a DNI is " + MAX_LENGTH);
        if (notification.hasErrors()) return Result.failure(notification);
        return Result.success(new Dni(value));
    }
}
