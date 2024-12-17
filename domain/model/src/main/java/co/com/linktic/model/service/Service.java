package co.com.linktic.model.service;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Service {
    private UUID id; // Identificador único
    private String name; // Nombre del servicio
    private String description; // Descripción del servicio
    private BigDecimal price; // Precio del servicio
    private String currency; // Moneda utilizada (COP)
}
