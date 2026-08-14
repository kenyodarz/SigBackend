package co.com.bancolombia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Contrato {
    private String idContrato;
    private String tipoContrato;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double salario;
    private Empleado empleado;
    private boolean liquidado;
}
