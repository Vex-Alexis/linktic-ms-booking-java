package co.com.linktic.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "Services")
public class ServiceEntity {
    @Id
    private UUID id; // Identificador único
    private String name; // Nombre del servicio
    private String description; // Descripción del servicio
    private BigDecimal price; // Precio del servicio
    private String currency; // Moneda utilizada (COP)
}
