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
@Table("entrega_dye")
public class EntregaDyEData {
    @Id
    private String idEntregaDyE;
    private LocalDate fechaEntregaDyE;
    private String descripcion;
    private String tipo;
    private String empleadoCedula;
}
