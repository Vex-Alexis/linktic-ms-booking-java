package co.com.linktic.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalMessage {
    CUSTOMER_NOT_FOUND("ERR001", "Customer not found"),
    SERVICE_NOT_FOUND("ERR002", "Service not found"),
    RESERVATION_NOT_FOUND("ERR003", "Reservation not found"),
    INVALID_STATUS("ERR004", "Invalid reservation status"),
    DATABASE_ERROR("ERR005", "Database operation failed"),
    BUSINESS_ERROR("ERR006", "A business error has occurred, Cause: "),
    INVALID_CUSTOMER_ID("ERR007", "Client ID is invalid or null");

    private final String code;
    private final String message;

}
