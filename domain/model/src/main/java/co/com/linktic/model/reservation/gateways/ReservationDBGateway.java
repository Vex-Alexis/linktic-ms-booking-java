package co.com.linktic.model.reservation.gateways;

import co.com.linktic.model.dto.PageResult;
import co.com.linktic.model.reservation.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Pageable;
import java.util.UUID;

public interface ReservationDBGateway {
    Mono<Reservation> save(Reservation reservation);
    Flux<Reservation> findAll();
    Mono<Reservation> update(Long id, Reservation reservation);
    Mono<Reservation> findById(UUID id);
    Mono<Void> deleteById(UUID reservationId);
    Mono<PageResult<Reservation>> getReservations(int page, int size);
}
