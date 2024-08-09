package com.acme.banking.platform.shared.domain.model.valueobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Notification {
    private final List<Error> errors = new ArrayList<>();
    private static final String SEPARATOR = "||";

    public void addError(String message) {
        errors.add(new Error(message));
    }

    public List<Error> getErrors() {
        return errors;
    }

    public String errorMessage() {
        return errors.stream().map(Error::message).collect(Collectors.joining(SEPARATOR + " "));
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
