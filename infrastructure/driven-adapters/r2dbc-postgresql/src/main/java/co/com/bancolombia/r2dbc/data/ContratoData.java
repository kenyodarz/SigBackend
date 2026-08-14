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
@Table("contratos")
public class ContratoData implements Persistable<String> {
    @Id
    private String idContrato;
    private String tipoContrato;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double salario;
    private String empleadoCedula;
    private boolean liquidado;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public String getId() {
        return idContrato;
    }

    @Override
    public boolean isNew() {
        return isNew || idContrato == null;
    }
}
