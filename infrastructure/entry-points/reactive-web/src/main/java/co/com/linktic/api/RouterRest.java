package co.com.linktic.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    private static final String BASE_PATH = "/api/reservation";


    @Bean
    public RouterFunction<ServerResponse> routerFunction(HandlerReservation handlerReservation) {
        return route()
                .POST(BASE_PATH, handlerReservation::createReservation)
                .PUT(BASE_PATH + "/{reservationId}", handlerReservation::modifyReservation)
                .DELETE(BASE_PATH + "/{reservationId}", handlerReservation::cancelReservation)
                .GET(BASE_PATH, handlerReservation::viewReservations)
                .GET(BASE_PATH + "/pageable", handlerReservation::getReservations)
                .GET(BASE_PATH +  "/customers/{customerId}", handlerReservation::getCustomer)
                .build();

    }



    /*
    @Bean
    public RouterFunction<ServerResponse> routerFunction(HandlerReservation handlerReservation) {
        return route(POST("api/reservation"), handlerReservation::createReservation)
                .andRoute(PUT("/api/reservation/{reservationId}"), handlerReservation::modifyReservation)
                .andRoute(DELETE("/api/reservation/{reservationId}"), handlerReservation::cancelReservation)
                .andRoute(GET("/api/reservations"), handlerReservation::viewReservations) // Obtener reserva con filtros
                .andRoute(GET("/api/customers/{customerId}"), handlerReservation::getCustomer); // Obtener cliente

    }*/
}
