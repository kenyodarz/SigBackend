package co.com.bancolombia.r2dbc.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("recomendaciones")
public class RecommendationData implements Persistable<String> {
    @Id
    private String idRecomendaciones;
    private String examenId;
    private String recommendation;
    private String tipoSeguimiento;
    private String primeraSeguimiento;
    private String segundaSeguimiento;
    private String terceraSeguimiento;
    private Date createAt;

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
