package co.com.bancolombia.model;

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
public class Capacitacion {
    private String idCapacitacion;
    private String tema;
    private LocalDate fecha;
    @Builder.Default
    private List<Empleado> empleados = new ArrayList<>();
}
