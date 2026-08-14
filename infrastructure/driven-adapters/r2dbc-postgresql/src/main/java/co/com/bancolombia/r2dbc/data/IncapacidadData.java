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
@Table("incapacidades")
public class IncapacidadData {
    @Id
    private String idIncapacidad;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String entidad;
    private String enfermedad;
    private String cie10Codigo;
    private String empleadoCedula;
    private String estado;
}
