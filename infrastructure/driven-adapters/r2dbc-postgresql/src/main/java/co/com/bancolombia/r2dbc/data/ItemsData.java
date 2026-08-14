package co.com.bancolombia.r2dbc.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("items")
public class ItemsData {
    @Id
    private String idItems;
    private String nombre;
    private String marca;
    private String talla;
    private String categoria;
    private String serial;
    private String color;
}
