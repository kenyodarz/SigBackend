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
@Table("items")
public class ItemsData implements Persistable<String> {
    @Id
    private String idItems;
    private String nombre;
    private String marca;
    private String talla;
    private String categoria;
    private String serial;
    private String color;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public String getId() {
        return idItems;
    }

    @Override
    public boolean isNew() {
        return isNew || idItems == null;
    }
}
