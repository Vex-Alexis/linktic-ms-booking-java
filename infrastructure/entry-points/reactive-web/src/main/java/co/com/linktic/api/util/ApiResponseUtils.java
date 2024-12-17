package co.com.linktic.api.util;

import co.com.linktic.api.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ApiResponseUtils {

    private ApiResponseUtils() {} // Constructor privado para evitar instanciación

    public static <T> Mono<ServerResponse> buildResponse(
            boolean success,
            String code,
            String message,
            HttpStatus status,
            T data,
            Map<String, Object> metadata) {

        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(success)
                .code(code)
                .message(message != null ? message : (success ? "Operation successful" : "An error occurred"))
                .data(data)
                .metadata(metadata != null ? metadata : Map.of())
                .build();

        logResponse(response, success);
        return ServerResponse.status(status).bodyValue(response);
    }

    private static void logResponse(ApiResponse<?> response, boolean success) {
        if (success) {
            log.info("Response sent: {}", response);
        } else {
            log.error("Error Response: {}", response);
        }
    }

    public static Mono<ServerResponse> successResponse(Object data, String message) {
        Map<String, Object> metadata = Map.of(
                "timestamp", Dateutil.formatDate(LocalDateTime.now()),
                "apiVersion", "v1.0"
        );
        return buildResponse(
                true,
                "SUCCESS",
                message,
                HttpStatus.OK,
                data,
                metadata
        );
    }

    public static Mono<ServerResponse> successResponse(Object data, String message, Map<String, Object> metadata) {
        return buildResponse(
                true,
                "SUCCESS",
                message,
                HttpStatus.OK,
                data,
                metadata
        );
    }

    public static Mono<ServerResponse> errorResponse(String message, HttpStatus status, Throwable e) {
        Map<String, Object> metadata = Map.of(
                "exceptionType", e != null ? e.getClass().getSimpleName() : "UnknownException",
                "cause", e != null ? determineErrorMessage(e) : "error de conexion"
        );
        return buildResponse(
                false,
                "ERROR",
                message,
                status,
                null,
                metadata
        );
    }


    private static String determineErrorMessage(Throwable e){
        // Tambien puedo validar y retornar un mensaje por el tipo de excepcion
        if (e.getMessage().contains("400 BAD_REQUEST \"Failed to read HTTP message\"")) {
            return "The request body is invalid, it might have a wrong format, invalid UUID, or missing required fields";
        }
        if (e.getMessage().contains("Failed to obtain R2DBC Connection")) {
            return "Failed to obtain connection to the database";
        }
        if (e.getMessage().contains("null value in column")) {
            return "A mandatory field is empty. Please provide the required fields";
        }


        return e.getMessage();
    }


}
