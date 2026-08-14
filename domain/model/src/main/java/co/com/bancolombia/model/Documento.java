package co.com.bancolombia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Documento {
    private String idDocumento;
    private Empleado empleado;
    private String tipo;
    private String nombre;
    private Date createAt;
    private byte[] archivo;
}
