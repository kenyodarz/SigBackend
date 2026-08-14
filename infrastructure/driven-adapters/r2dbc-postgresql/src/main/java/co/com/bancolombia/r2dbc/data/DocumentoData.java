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
@Table("documentos")
public class DocumentoData implements Persistable<String> {
    @Id
    private String idDocumento;
    private String empleadoCedula;
    private String tipo;
    private String nombre;
    private Date createAt;
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
