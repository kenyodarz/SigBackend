package co.com.bancolombia.r2dbc.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("cie10")
public class CIE10Data implements Persistable<String> {
    @Id
    private String codigo;
    private String descripcion;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public String getId() {
        return codigo;
    }

    @Override
    public boolean isNew() {
        return isNew || codigo == null;
    }
}
