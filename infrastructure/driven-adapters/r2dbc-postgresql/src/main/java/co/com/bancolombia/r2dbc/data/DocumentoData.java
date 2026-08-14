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
@Table("documentos")
public class DocumentoData implements Persistable<String> {

    @Id
    @Column("id_documento")
    private String idDocumento;

    @Column("empleado_cedula")
    private String empleadoCedula;

    private String tipo;
    private String nombre;

    @Column("create_at")
    private LocalDateTime createAt;

    private byte[] archivo;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public String getId() {
        return idDocumento;
    }

    @Override
    public boolean isNew() {
        return isNew || idDocumento == null;
    }
}
