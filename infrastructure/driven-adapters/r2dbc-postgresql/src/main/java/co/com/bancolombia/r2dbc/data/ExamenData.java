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
@Table("examenes")
public class ExamenData {
    @Id
    private String idExamen;
    private LocalDate fecha;
    private boolean concepto;
    @Builder.Default
    private String restriccion = "Sin Restricción";
    private String tipoExamen;
    private String contratoId;
}
