package co.com.bancolombia.r2dbc.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("vacaciones")
public class VacacionesData {
    @Id
    private String idVacaciones;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String contratoId;
}
