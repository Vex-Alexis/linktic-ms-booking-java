package co.com.linktic.model.service.gateways;

import co.com.linktic.model.service.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ServiceDBGateway {
    Mono<Service> findById(UUID id);
}
