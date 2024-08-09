package com.acme.banking.platform.shared.domain.model.valueobjects;

public record PersonName(String firstName, String lastName) {
    private static final int MAX_LENGTH = 75;

    public static Result<PersonName, Notification> create(String firstName, String lastName) {
        Notification notification = new Notification();
        firstName = firstName.trim();
        lastName = lastName.trim();
        if (firstName.isEmpty()) notification.addError("firstName is required");
        if (lastName.isEmpty()) notification.addError("lastName is required");
        if (firstName.length() > MAX_LENGTH)
            notification.addError("The maximum length of a firstName is " + MAX_LENGTH + " characters including spaces");
        if (lastName.length() > MAX_LENGTH)
            notification.addError("The maximum length of a lastName is " + MAX_LENGTH + " characters including spaces");
        if (notification.hasErrors()) return Result.failure(notification);
        return Result.success(new PersonName(firstName, lastName));
    }
}
