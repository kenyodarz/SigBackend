package co.com.bancolombia.model;

import co.com.bancolombia.model.integrations.CIE10;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Incapacidad {
    private String idIncapacidad;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String entidad;
    private String enfermedad;
    private CIE10 cie10;
    private Empleado empleado;
    private String estado;
}
