package co.com.bancolombia.model.integrations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Items {
    private String idItems;
    private String nombre;
    private String marca;
    private String talla;
    private String categoria;
    private String serial;
    private String color;
}
