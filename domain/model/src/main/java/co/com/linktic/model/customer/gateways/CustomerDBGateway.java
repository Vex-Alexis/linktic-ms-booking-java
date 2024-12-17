package co.com.linktic.model.customer.gateways;

import co.com.linktic.model.customer.Customer;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

public interface CustomerDBGateway {
    Mono<Customer> findById(UUID id);
}
