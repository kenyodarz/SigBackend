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

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("recomendaciones")
public class RecommendationData implements Persistable<String> {

    @Id
    @Column("id_recomendaciones")
    private String idRecomendaciones;

    @Column("examen_id")
    private String examenId;

    private String recommendation;

    @Column("tipo_seguimiento")
    private String tipoSeguimiento;

    @Column("primera_seguimiento")
    private String primeraSeguimiento;

    @Column("segunda_seguimiento")
    private String segundaSeguimiento;

    @Column("tercera_seguimiento")
    private String terceraSeguimiento;

    @Column("create_at")
    private LocalDateTime createAt;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public String getId() {
        return idRecomendaciones;
    }

    @Override
    public boolean isNew() {
        return isNew || idRecomendaciones == null;
    }
}
