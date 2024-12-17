package co.com.linktic.r2dbc.adapter;

import co.com.linktic.model.dto.PageResult;
import co.com.linktic.model.reservation.Reservation;
import co.com.linktic.model.reservation.gateways.ReservationDBGateway;
import co.com.linktic.r2dbc.entity.ReservationEntity;
import co.com.linktic.r2dbc.helper.ReactiveAdapterOperations;
import co.com.linktic.r2dbc.repository.ReservationRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public class ReservationAdapter extends ReactiveAdapterOperations<Reservation, ReservationEntity, UUID, ReservationRepository> implements ReservationDBGateway {

    public ReservationAdapter(ReservationRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Reservation.class));
    }

    @Override
    public Mono<Reservation> update(Long id, Reservation reservation) {
        return Mono.error(new UnsupportedOperationException("Method not implemented yet"));
    }

    @Override
    public Mono<Void> deleteById(UUID reservationId) {
        return repository.deleteById(reservationId);
    }

    /*@Override
    public Flux<Reservation> getReservations(int page, int size) {
        return repository.findAll()
                .skip((long) page * size)
                .take(size)
                .map(this::toEntity);
    }*/

    @Override
    public Mono<PageResult<Reservation>> getReservations(int page, int size) {
        return repository.findAll()
                .skip((long) page * size)
                .take(size)
                .map(this::toEntity)
                .collectList()
                .zipWith(repository.count())
                .map(tuple -> {
                    List<Reservation> reservations = tuple.getT1();
                    long totalElements = tuple.getT2();
                    int totalPages = (int) Math.ceil((double) totalElements / size);
                    return new PageResult<>(reservations, page, size, totalElements, totalPages);
                });
    }

}
