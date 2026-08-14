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
@Table("capacitaciones")
public class CapacitacionData implements Persistable<String> {

    @Id
    @Column("id_capacitacion")
    private String idCapacitacion;

    private String tema;
    private LocalDate fecha;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public String getId() {
        return idCapacitacion;
    }

    @Override
    public boolean isNew() {
        return isNew || idCapacitacion == null;
    }
}
