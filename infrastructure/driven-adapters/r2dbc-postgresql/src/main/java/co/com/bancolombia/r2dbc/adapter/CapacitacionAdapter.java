package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Capacitacion;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.gateways.CapacitacionGateway;
import co.com.bancolombia.r2dbc.data.CapacitacionData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.CapacitacionRepository;
import co.com.bancolombia.r2dbc.repository.EmpleadoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public class CapacitacionAdapter extends ReactiveAdapterOperations<Capacitacion, CapacitacionData, String, CapacitacionRepository> implements CapacitacionGateway {

    private final EmpleadoRepository empleadoRepository;
    private final DatabaseClient databaseClient;

    public CapacitacionAdapter(CapacitacionRepository repository, ObjectMapper mapper, EmpleadoRepository empleadoRepository, DatabaseClient databaseClient) {
        super(repository, mapper, d -> mapper.map(d, Capacitacion.class));
        this.empleadoRepository = empleadoRepository;
        this.databaseClient = databaseClient;
    }

    private Mono<Capacitacion> enrichCapacitacion(CapacitacionData d) {
        if (d == null) {
            return Mono.empty();
        }
        Capacitacion cap = mapper.map(d, Capacitacion.class);

        return databaseClient.sql("SELECT e.* FROM empleados e JOIN capacitaciones_empleados ce ON e.cedula = ce.empleado_cedula WHERE ce.capacitacion_id = :capId")
                .bind("capId", d.getIdCapacitacion())
                .map((row, metadata) -> Empleado.builder()
                        .cedula(row.get("cedula", String.class))
                        .nombres(row.get("nombres", String.class))
                        .apellidos(row.get("apellidos", String.class))
                        .genero(row.get("genero", String.class))
                        .direccion(row.get("direccion", String.class))
                        .municipio(row.get("municipio", String.class))
                        .telefono(row.get("telefono", String.class))
                        .alergia(row.get("alergia", String.class))
                        .medicamentos(row.get("medicamentos", String.class))
                        .enCasoEmergencia(row.get("en_caso_emergencia", String.class))
                        .parentesco(row.get("parentesco", String.class))
                        .telEmergencia(row.get("tel_emergencia", String.class))
                        .build())
                .all()
                .collectList()
                .map(empleados -> {
                    cap.setEmpleados(empleados);
                    return cap;
                })
                .defaultIfEmpty(cap);
    }

    @Override
    public Mono<Capacitacion> findById(String id) {
        return repository.findById(id).flatMap(this::enrichCapacitacion);
    }

    @Override
    public Flux<Capacitacion> findAll() {
        return repository.findAll().flatMap(this::enrichCapacitacion);
    }

    @Override
    public Mono<Capacitacion> save(Capacitacion entity) {
        CapacitacionData data = toData(entity);
        return saveData(data).flatMap(this::enrichCapacitacion);
    }

    @Override
    public Mono<Capacitacion> asignarEmpleados(String id, List<Empleado> empleados) {
        if (empleados == null || empleados.isEmpty()) {
            return findById(id);
        }
        return Flux.fromIterable(empleados)
                .flatMap(emp -> {
                    if (emp == null || emp.getCedula() == null) {
                        return Mono.empty();
                    }
                    return databaseClient.sql("INSERT INTO capacitaciones_empleados (capacitacion_id, empleado_cedula) VALUES (:capId, :cedula) ON CONFLICT DO NOTHING")
                            .bind("capId", id)
                            .bind("cedula", emp.getCedula())
                            .then();
                })
                .then(findById(id));
    }

    @Override
    public Mono<Capacitacion> eliminarEmpleado(String id, Empleado empleado) {
        if (empleado == null || empleado.getCedula() == null) {
            return findById(id);
        }
        return databaseClient.sql("DELETE FROM capacitaciones_empleados WHERE capacitacion_id = :capId AND empleado_cedula = :cedula")
                .bind("capId", id)
                .bind("cedula", empleado.getCedula())
                .then()
                .then(findById(id));
    }

    @Override
    public Flux<Capacitacion> buscarPorEmpleado(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return Flux.empty();
        }
        return databaseClient.sql("SELECT c.* FROM capacitaciones c JOIN capacitaciones_empleados ce ON c.id_capacitacion = ce.capacitacion_id WHERE ce.empleado_cedula = :cedula")
                .bind("cedula", cedula)
                .map((row, metadata) -> CapacitacionData.builder()
                        .idCapacitacion(row.get("id_capacitacion", String.class))
                        .tema(row.get("tema", String.class))
                        .build())
                .all()
                .flatMap(this::enrichCapacitacion);
    }

    @Override
    protected Mono<CapacitacionData> saveData(CapacitacionData data) {
        if (data != null) {
            if (data.getIdCapacitacion() == null || data.getIdCapacitacion().trim().isEmpty()) {
                data.setIdCapacitacion("CAP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                data.setNew(true);
                return repository.save(data);
            }
            return repository.existsById(data.getIdCapacitacion())
                    .flatMap(exists -> {
                        data.setNew(!Boolean.TRUE.equals(exists));
                        return repository.save(data);
                    });
        }
        return super.saveData(data);
    }
}
