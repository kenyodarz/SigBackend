package co.com.bancolombia.model.integrations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Afp {
    private String nit;
    private String nombre;
    private String direccion;
    private String telefono;
}
