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
public class Examen {
    private String idExamen;
    private LocalDate fecha;
    private boolean concepto;
    @Builder.Default
    private String restriccion = "Sin Restricción";
    private String tipoExamen;
    private Contrato contrato;
}
