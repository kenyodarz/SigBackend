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
@Table("empleados")
public class EmpleadoData implements Persistable<String> {
    @Id
    private String cedula;
    private String nombres;
    private String apellidos;
    private String genero;
    private String fechaNacimiento;
    private String tipoSangre;
    private String direccion;
    private String municipio;
    private String telefono;
    private String epsNit;
    private String afpNit;
    private String arlNit;
    private String cajaComFamiliarNit;
    private String alergia;
    private String medicamentos;
    private String enCasoEmergencia;
    private String parentesco;
    private String telEmergencia;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public String getId() {
        return cedula;
    }

    @Override
    public boolean isNew() {
        return isNew || cedula == null;
    }
}
