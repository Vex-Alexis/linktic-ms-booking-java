package co.com.linktic.api.exception;

import co.com.linktic.api.dto.response.ApiResponse;
import co.com.linktic.api.util.ApiResponseUtils;
import co.com.linktic.model.exceptions.BusinessException;
import co.com.linktic.model.exceptions.CustomerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {
    public GlobalExceptionHandler(ErrorAttributes errorAttributes,
                                  WebProperties.Resources resources,
                                  ApplicationContext applicationContext,
                                  ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources, applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }


    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Throwable e = getError(request);

        log.error("Global Ex Handler: {}", e.getMessage());


        if (e instanceof IllegalArgumentException) {
            return ApiResponseUtils.errorResponse("Malformed request body or query parameters.", HttpStatus.BAD_REQUEST, e);
        }else if (e instanceof ServerWebInputException) {
            return ApiResponseUtils.errorResponse("The provided input is invalid. Please check and try again.", HttpStatus.BAD_REQUEST, e);
        }else if (e instanceof DateTimeParseException) {
            return ApiResponseUtils.errorResponse("Invalid date provided. Use the format 'YYYY-MM-DD' and try again.", HttpStatus.BAD_REQUEST, e);
        }

        // Manejo genérico para otras excepciones
        return ApiResponseUtils.errorResponse(
                "An unexpected error occurred. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                e
        );
    }













}
