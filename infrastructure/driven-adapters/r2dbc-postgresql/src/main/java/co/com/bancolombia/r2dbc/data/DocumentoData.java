package co.com.bancolombia.r2dbc.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("documentos")
public class DocumentoData {
    @Id
    private String idDocumento;
    private String empleadoCedula;
    private String tipo;
    private String nombre;
    private Date createAt;
    private byte[] archivo;
}
