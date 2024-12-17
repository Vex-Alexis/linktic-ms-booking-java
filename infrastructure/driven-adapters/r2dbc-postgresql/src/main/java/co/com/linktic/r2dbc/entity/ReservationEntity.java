package co.com.linktic.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "Reservations")
public class ReservationEntity {
    @Id
    private UUID id;
    private UUID customerId;
    private UUID serviceId;
    private LocalDateTime serviceDate;
    private String status;
    private String paymentStatus;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}