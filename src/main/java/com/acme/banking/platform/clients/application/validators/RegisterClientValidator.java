package com.acme.banking.platform.clients.application.validators;

import com.acme.banking.platform.clients.domain.commands.RegisterClient;
import com.acme.banking.platform.clients.domain.processors.ClientDni;
import com.acme.banking.platform.clients.infrastructure.persistence.jpa.repositories.ClientDniRepository;
import com.acme.banking.platform.shared.domain.model.valueobjects.Notification;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class RegisterClientValidator {
    private final ClientDniRepository clientDniRepository;
    private static final int DNI_MAX_LENGTH = 8;

    public RegisterClientValidator(ClientDniRepository clientDniRepository) {
        this.clientDniRepository = clientDniRepository;
    }

    public Notification validate(RegisterClient command)
    {
        Notification notification = new Notification();

        String firstName = command.getFirstName().trim();
        if (firstName.isEmpty()) notification.addError("Client firstname is required");

        String lastName = command.getLastName().trim();
        if (lastName.isEmpty()) notification.addError("Client lastname is required");

        String dni = command.getDni().trim();
        if (dni.isEmpty()) notification.addError("Client dni is required");

        if (dni.length() != DNI_MAX_LENGTH) notification.addError("Client dni must be " + DNI_MAX_LENGTH + " characters");

        if (notification.hasErrors()) return notification;

        Optional<ClientDni> optional = clientDniRepository.getByDni(dni);
        if (optional.isPresent()) notification.addError("Client dni is taken");

        return notification;
    }
}
