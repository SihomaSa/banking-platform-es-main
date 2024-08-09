package com.acme.banking.platform.clients.domain.model.aggregates;

import com.acme.banking.platform.clients.domain.commands.*;
import com.acme.banking.platform.clients.domain.events.*;
import com.acme.banking.platform.clients.domain.model.valueobjects.ClientStatus;
import com.acme.banking.platform.shared.domain.model.valueobjects.Dni;
import com.acme.banking.platform.shared.domain.model.valueobjects.PersonName;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Client {
    @AggregateIdentifier
    private Long id;
    private PersonName name;
    private Dni dni;
    private ClientStatus status;

    public Client() {
    }

    @CommandHandler
    public Client(RegisterClient command) {
        ClientRegistered event = new ClientRegistered(
            command.getId(),
            command.getFirstName(),
            command.getLastName(),
            command.getDni(),
            command.getCreatedAt()
        );
        apply(event);
    }

    @CommandHandler
    public void handle(EditClient command) {
        ClientEdited event = new ClientEdited(
            command.getId(),
            command.getFirstName(),
            command.getLastName(),
            command.getDni(),
            command.getUpdatedAt()
        );
        apply(event);
    }

    @EventSourcingHandler
    protected void on(ClientRegistered event) {
        id = event.getId();
        name = PersonName.create(event.getFirstName(), event.getLastName()).getSuccess();
        dni = Dni.create(event.getDni()).getSuccess();
        status = ClientStatus.ACTIVE;
    }

    @EventSourcingHandler
    protected void on(ClientEdited event) {
        name = PersonName.create(event.getFirstName(), event.getLastName()).getSuccess();
        dni = Dni.create(event.getDni()).getSuccess();
    }
}
