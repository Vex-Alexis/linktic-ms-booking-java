package co.com.linktic.r2dbc.repository;

import co.com.linktic.r2dbc.entity.ReservationEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ReservationRepository extends ReactiveCrudRepository<ReservationEntity, UUID>, ReactiveQueryByExampleExecutor<ReservationEntity> {
}
