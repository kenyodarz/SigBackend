package co.com.bancolombia.r2dbc.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("vacaciones")
public class VacacionesData implements Persistable<String> {
    @Id
    private String idVacaciones;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String contratoId;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public String getId() {
        return idVacaciones;
    }

    @Override
    public boolean isNew() {
        return isNew || idVacaciones == null;
    }
}
