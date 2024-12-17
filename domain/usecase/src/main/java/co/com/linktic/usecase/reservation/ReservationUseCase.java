package co.com.linktic.usecase.reservation;

import co.com.linktic.model.customer.Customer;
import co.com.linktic.model.customer.gateways.CustomerDBGateway;
import co.com.linktic.model.dto.PageResult;
import co.com.linktic.model.enums.TechnicalMessage;
import co.com.linktic.model.exceptions.BusinessException;
import co.com.linktic.model.exceptions.CustomerException;
import co.com.linktic.model.exceptions.ServiceException;
import co.com.linktic.model.reservation.Reservation;
import co.com.linktic.model.reservation.ReservationRequest;
import co.com.linktic.model.reservation.gateways.ReservationDBGateway;
import co.com.linktic.model.service.gateways.ServiceDBGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Pageable;
import java.rmi.ServerException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ReservationUseCase {

    private final ReservationDBGateway reservationDBGateway;
    private final CustomerDBGateway customerDBGateway;
    private final ServiceDBGateway serviceDBGateway;


    public Mono<Reservation> createReservation(ReservationRequest reservationRequest){
        Reservation reservation = Reservation.builder()
                .customerId(reservationRequest.getCustomerId())
                .serviceId(reservationRequest.getServiceId())
                .serviceDate(reservationRequest.getServiceDate())
                .status(reservationRequest.getStatus())
                .paymentStatus(reservationRequest.getPaymentStatus())
                .notes(reservationRequest.getNotes())
                .createdAt(LocalDateTime.now()) // Asigna la fecha de creación actual
                .updatedAt(LocalDateTime.now())
                .build();

        return Mono.zip(
                customerDBGateway.findById(reservation.getCustomerId())
                        .switchIfEmpty(Mono.error(new CustomerException(TechnicalMessage.CUSTOMER_NOT_FOUND))),
                serviceDBGateway.findById(reservation.getServiceId())
                        .switchIfEmpty(Mono.error(new ServiceException(TechnicalMessage.SERVICE_NOT_FOUND)))
        )
                .flatMap(tuple -> reservationDBGateway.save(reservation))
                .doOnError(e -> System.out.println("Error in ReservationUseCase: " + e.getMessage()));
    }

    public Mono<Reservation> modifyReservation(UUID reservationId, Reservation updatedReservation){
        return reservationDBGateway.findById(reservationId)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.RESERVATION_NOT_FOUND, ", with ID: " + reservationId)))
                .flatMap(existingReservation ->
                        serviceDBGateway.findById(updatedReservation.getServiceId())
                                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.SERVICE_NOT_FOUND, ", with ID: " + updatedReservation.getServiceId())))
                                .then(Mono.just(existingReservation))
                )
                .flatMap(existingReservation -> {
                    existingReservation.setServiceId(updatedReservation.getServiceId());
                    existingReservation.setServiceDate(updatedReservation.getServiceDate());
                    existingReservation.setStatus(updatedReservation.getStatus());
                    existingReservation.setPaymentStatus(updatedReservation.getPaymentStatus());
                    existingReservation.setNotes(updatedReservation.getNotes());
                    existingReservation.setUpdatedAt(LocalDateTime.now());

                    System.out.println("Se procede a guardar la reserva actualizada.");
                    return reservationDBGateway.save(existingReservation);
                });
    }

    public Mono<Void> cancelReservation(UUID reservationId) {
        return reservationDBGateway.findById(reservationId)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.RESERVATION_NOT_FOUND, ", with ID: " + reservationId)))
                .flatMap(reservation -> reservationDBGateway.deleteById(reservationId))
                .doOnError(e -> System.out.println("Error cancelling reservation in ReservationUseCase: " + e.getMessage()));
                //.onErrorMap(e -> new Exception(e.getMessage()));
    }


    public Flux<Reservation> viewReservations(Optional<UUID> reservationId, Optional<UUID> customerId, Optional<UUID> serviceId, Optional<String> statusReservation,Optional<LocalDate> startDate, Optional<LocalDate> endDate) {


        return reservationDBGateway.findAll()
                .filter(reservation -> reservationId.map(id -> reservation.getId().equals(id)).orElse(true))
                .filter(reservation -> customerId.map(id -> reservation.getCustomerId().equals(id)).orElse(true))
                .filter(reservation -> serviceId.map(id -> reservation.getServiceId().equals(id)).orElse(true))
                .filter(reservation -> statusReservation.map(status -> reservation.getStatus().equals(status)).orElse(true))
                .filter(reservation -> startDate.map(date -> reservation.getServiceDate().toLocalDate().isAfter(date.minusDays(1))).orElse(true))
                .filter(reservation -> endDate.map(date -> reservation.getServiceDate().toLocalDate().isBefore(date.plusDays(1))).orElse(true))
                .doOnError(e -> System.out.println("Error viewing reservations in ReservationUseCase: " + e.getMessage()));

    }

    // Obtener todas las reservas para implementar paginacion
    public Mono<PageResult<Reservation>> getPaginatedReservations(int page, int size){
        return reservationDBGateway.getReservations(page, size);
    }

    // Obtener Cliente
    public Mono<Customer> getCustomer(UUID customerId) {

        System.out.println("El Id en el useCase es: " + customerId);

        return customerDBGateway.findById(customerId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cliente no encontrado con ID: " + customerId)));
    }




}
