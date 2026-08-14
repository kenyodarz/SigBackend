package co.com.bancolombia.model;

import co.com.bancolombia.model.integrations.Afp;
import co.com.bancolombia.model.integrations.Arl;
import co.com.bancolombia.model.integrations.CajaComFamiliar;
import co.com.bancolombia.model.integrations.Eps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Empleado {
    private String cedula;
    private String nombres;
    private String apellidos;
    private String genero;
    private String fechaNacimiento;
    private String tipoSangre;
    private String direccion;
    private String municipio;
    private String telefono;
    private Eps eps;
    private Afp afp;
    private Arl arl;
    private CajaComFamiliar cajaComFamiliar;
    private String alergia;
    private String medicamentos;
    private String enCasoEmergencia;
    private String parentesco;
    private String telEmergencia;
}
