package co.com.linktic.model.reservation;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Reservation {
    private UUID id; // Identificador único de la reserva
    private UUID customerId; // ID del cliente asociado
    private UUID serviceId; // ID del servicio asociado
    private LocalDateTime serviceDate; // Fecha y hora del servicio reservado
    private String status; // Estado de la reserva (PENDING, CONFIRMED, CANCELLED)
    private String paymentStatus; // Estado del pago (PENDING, PAID, FAILED)
    private String notes; // Notas adicionales
    private LocalDateTime createdAt; // Fecha de creación del registro
    private LocalDateTime updatedAt; // Fecha de última actualización del registro
}
