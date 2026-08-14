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
@Table("examenes")
public class ExamenData implements Persistable<String> {

    @Id
    @Column("id_examen")
    private String idExamen;

    private LocalDate fecha;
    private boolean concepto;

    @Builder.Default
    private String restriccion = "Sin Restricción";

    @Column("tipo_examen")
    private String tipoExamen;

    @Column("contrato_id")
    private String contratoId;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public String getId() {
        return idExamen;
    }

    @Override
    public boolean isNew() {
        return isNew || idExamen == null;
    }
}
