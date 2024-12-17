package co.com.linktic.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {
    private UUID customerId;
    private UUID serviceId;
    private LocalDateTime serviceDate;
    private String status;
    private String paymentStatus;
    private String notes;
}
