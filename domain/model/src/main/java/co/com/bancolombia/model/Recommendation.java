package co.com.bancolombia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Recommendation {
    private String idRecomendaciones;
    private Examen examen;
    private String recommendation;
    private String tipoSeguimiento;
    private String primeraSeguimiento;
    private String segundaSeguimiento;
    private String terceraSeguimiento;
    private Date createAt;
}
