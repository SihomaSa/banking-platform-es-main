package com.acme.banking.platform.shared.infrastructure.axon;

import com.acme.banking.platform.accounts.domain.model.aggregates.Account;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfig {
    @Primary
    @Bean
    public Repository<Account> accountEventSourcingRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(Account.class)
            .eventStore(eventStore)
            .build();
    }
}
