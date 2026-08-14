package co.com.bancolombia.r2dbc.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("incapacidades")
public class IncapacidadData implements Persistable<String> {

    @Id
    @Column("id_incapacidad")
    private String idIncapacidad;

    @Column("fecha_inicio")
    private LocalDate fechaInicio;

    @Column("fecha_fin")
    private LocalDate fechaFin;

    private String entidad;
    private String enfermedad;

    @Column("cie10_codigo")
    private String cie10Codigo;

    @Column("empleado_cedula")
    private String empleadoCedula;

    private String estado;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public String getId() {
        return idIncapacidad;
    }

    @Override
    public boolean isNew() {
        return isNew || idIncapacidad == null;
    }
}
