package co.com.bancolombia.model;

import co.com.bancolombia.model.integrations.Items;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EntregaDyE {
    private String idEntregaDyE;
    private LocalDate fechaEntregaDyE;
    private String descripcion;
    private String tipo;
    private Empleado empleado;
    @Builder.Default
    private List<Items> items = new ArrayList<>();
}
