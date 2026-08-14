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
public class Vacaciones {
    private String idVacaciones;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Contrato contrato;
}
