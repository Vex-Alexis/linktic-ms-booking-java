package co.com.linktic.api.dto.response;

import co.com.linktic.model.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ReservationResponse {
    private UUID customerId;
    private UUID serviceId;
    private LocalDateTime serviceDate;
    private String status;
    private String paymentStatus;
    private String notes;

    // Constructor para convertir desde la entidad
    public ReservationResponse(Reservation reservation) {
        this.customerId = reservation.getCustomerId();
        this.serviceId = reservation.getServiceId();
        this.serviceDate = reservation.getServiceDate();
        this.status = reservation.getStatus();
        this.paymentStatus = reservation.getPaymentStatus();
        this.notes = reservation.getNotes();
    }


}
