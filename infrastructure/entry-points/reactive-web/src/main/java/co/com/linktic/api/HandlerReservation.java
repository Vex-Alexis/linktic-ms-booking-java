package co.com.linktic.api;

import co.com.linktic.api.dto.response.ReservationResponse;
import co.com.linktic.api.util.ApiResponseUtils;
import co.com.linktic.model.dto.PageResult;
import co.com.linktic.model.enums.TechnicalMessage;
import co.com.linktic.model.exceptions.BusinessException;
import co.com.linktic.model.exceptions.CustomerException;
import co.com.linktic.model.exceptions.ServiceException;
import co.com.linktic.model.reservation.Reservation;
import co.com.linktic.model.reservation.ReservationRequest;
import co.com.linktic.usecase.reservation.ReservationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HandlerReservation {

    private final ReservationUseCase reservationUseCase;


    // Metodo para crear una nueva reserva
    public Mono<ServerResponse> createReservation(ServerRequest request){
        System.out.println("Request: " + request);
        return request.bodyToMono(ReservationRequest.class)
                .doOnNext(req -> log.info("Received Request for create reservation: {}", req))
                .flatMap(this::validateRequest)
                .flatMap(reservationUseCase::createReservation) // Llama al caso de uso
                .flatMap(reservation -> {
                    //ReservationResponse response = new ReservationResponse(reservation);
                    return ApiResponseUtils.successResponse(reservation, "Reservation created successfully");
                })
                .doOnError(e -> log.error("The reservation was not created"))
                .onErrorResume(e -> handleError(e, "Error creating reservation"));
    }


    public Mono<ServerResponse> modifyReservation(ServerRequest request) {
        UUID reservationId = UUID.fromString(request.pathVariable("reservationId"));
        return request.bodyToMono(Reservation.class)
                .doOnNext(req -> log.info("Request received to modify reservation: {}", req))
                //.flatMap(reservation -> this.validateRequestModify(reservationId, reservation))
                .flatMap(reservation -> reservationUseCase.modifyReservation(reservationId, reservation))
                .flatMap(updatedReservation ->
                        ApiResponseUtils.successResponse(updatedReservation, "Reservation modified successfully")
                )
                .doOnError(e -> log.error("The reservation was not modified"))
                .onErrorResume(e -> handleError(e, "Error modifying reservation"));
    }

    public Mono<ServerResponse> cancelReservation(ServerRequest request) {
        UUID reservationId = UUID.fromString(request.pathVariable("reservationId"));
        return reservationUseCase.cancelReservation(reservationId)
                .then(ApiResponseUtils.successResponse(
                        null,
                        "Reservation cancelled successfully"
                ))
                .doOnError(e -> log.error("The reservation was not cancelled"))
                .onErrorResume(e -> handleError(e, "Error cancelling reservation"));
    }


    // Obtener reservas con filtros
    public Mono<ServerResponse> viewReservations(ServerRequest request) {
        log.info("Fetching reservations with filters");
        Optional<UUID> reservationId = request.queryParam("reservationId").map(UUID::fromString);
        Optional<UUID> customerId = request.queryParam("customerId").map(UUID::fromString);
        Optional<UUID> serviceId = request.queryParam("serviceId").map(UUID::fromString);
        Optional<String> statusReservation = request.queryParam("statusReservation");
        Optional<LocalDate> startDate = request.queryParam("startDate").map(LocalDate::parse);
        Optional<LocalDate> endDate = request.queryParam("endDate").map(LocalDate::parse);

        return reservationUseCase.viewReservations(reservationId, customerId, serviceId, statusReservation,startDate, endDate)
                .collectList()
                .flatMap(reservations -> {
                    if (reservations.isEmpty()) {
                        log.info("No reservations found with the provided filters");
                        return ApiResponseUtils.successResponse(
                                reservations,
                                "No reservations found matching the provided filters"
                        );
                    }
                    log.info("Reservations retrieved successfully: {}", reservations.size());
                    return ApiResponseUtils.successResponse(reservations, "Reservations retrieved successfully");
                })
                .doOnError(e -> log.error("Reservations not retrieved"))
                .onErrorResume(e -> handleError(e, "Error retrieving reservations"));
    }


    public Mono<ServerResponse> getReservations(ServerRequest request){

        int page = request.queryParam("page").map(Integer::parseInt).orElse(0);
        int size = request.queryParam("size").map(Integer::parseInt).orElse(10);
        page += -1;

        return reservationUseCase.getPaginatedReservations(page, size)
                .flatMap(pageResult -> {
                    Map<String, Object> metadata = new HashMap<>();
                    metadata.put("currentPage", pageResult.getCurrentPage() + 1);
                    metadata.put("pageSize", pageResult.getPageSize());
                    metadata.put("totalPages", pageResult.getTotalPages());
                    metadata.put("totalElements", pageResult.getTotalElements());
                    metadata.put("returnedElements", pageResult.getContent().size());

                    log.info("Reservations retrieved successfully");

                    return ApiResponseUtils.successResponse(pageResult.getContent(), "Reservations retrieved successfully", metadata);
                })
                .onErrorResume(e ->{
                    log.error("Error fetching paginated reservations");
                    return handleError(e, "Error fetching paginated reservations");
                });
    }





    // TODO: Otros metodos

    // obtener cliente
    public Mono<ServerResponse> getCustomer(ServerRequest request){
        UUID customerId = UUID.fromString(request.pathVariable("customerId"));
        return reservationUseCase.getCustomer(customerId)
                .flatMap(customer -> {
                    return ApiResponseUtils.successResponse(customer, "Customer retrieved successfully");
                })
                .doOnError(e -> log.error("Customer not retrieved"))
                .onErrorResume(e -> handleError(e, "Error retrieving customer"));
    }


    // Manejo centralizado de errores
    private Mono<ServerResponse> handleError(Throwable e, String defaultMessage) {
        log.error("{}: {}", defaultMessage, e.getMessage());
        if (e instanceof CustomerException) {
            return ApiResponseUtils.errorResponse(defaultMessage, HttpStatus.BAD_REQUEST, e);
        }else if (e instanceof ServiceException) {
            // Manejar errores de entrada (como deserialización fallida)
            return  ApiResponseUtils.errorResponse(defaultMessage, HttpStatus.BAD_REQUEST, e);
        }else if (e instanceof BusinessException) {
            // Manejar errores de entrada (como deserialización fallida)
            return ApiResponseUtils.errorResponse(defaultMessage, HttpStatus.BAD_REQUEST, e);
        }else if (e instanceof ServerWebInputException) {
            // Manejar errores de entrada (como deserialización fallida)
            return ApiResponseUtils.errorResponse(defaultMessage, HttpStatus.BAD_REQUEST, e);
        }
        else if (e instanceof IllegalArgumentException) {
            // Manejar errores de entrada (como deserialización fallida)
            return ApiResponseUtils.errorResponse(defaultMessage, HttpStatus.BAD_REQUEST, e);
        }

        // Manejo de errores genéricos
        return ApiResponseUtils.errorResponse(defaultMessage, HttpStatus.INTERNAL_SERVER_ERROR, e);
    }



    // TODO: metodos auxiliares

    private Mono<ReservationRequest> validateRequest(ReservationRequest request) {

        if (request.getServiceDate() == null) {
            return Mono.error(new IllegalArgumentException("Service date cannot be null"));
        }
        // Aquí puedes agregar más validaciones específicas según tus reglas de negocio
        return Mono.just(request);
    }


}
