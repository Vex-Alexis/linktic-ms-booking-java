package co.com.linktic.api.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class ReservationRequest {
    private UUID customerId;
    private UUID serviceId;
    private LocalDateTime serviceDate;
    private String status;
    private String paymentStatus;
    private String notes;
}
