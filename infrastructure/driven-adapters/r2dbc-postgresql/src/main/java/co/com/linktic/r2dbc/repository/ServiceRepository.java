package co.com.linktic.r2dbc.repository;

import co.com.linktic.r2dbc.entity.ServiceEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ServiceRepository extends ReactiveCrudRepository<ServiceEntity, UUID>, ReactiveQueryByExampleExecutor<ServiceEntity> {
}

