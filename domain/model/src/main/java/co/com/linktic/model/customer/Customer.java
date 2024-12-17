package co.com.linktic.model.customer;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Customer {
    private UUID id; // Identificador único
    private String firstName; // Nombre del cliente
    private String lastName; // Apellido del cliente
    private String email; // Correo electrónico
    private String phoneNumber; // Número de celular
    private String documentType; // Tipo de documento (CC, TI, etc.)
    private String documentNumber; // Número de documento
}
