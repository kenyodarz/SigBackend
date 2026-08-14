package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.Incapacidad;
import co.com.bancolombia.model.gateways.IncapacidadGateway;
import co.com.bancolombia.r2dbc.data.IncapacidadData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.EmpleadoRepository;
import co.com.bancolombia.r2dbc.repository.IncapacidadRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class IncapacidadAdapter extends ReactiveAdapterOperations<Incapacidad, IncapacidadData, String, IncapacidadRepository> implements IncapacidadGateway {

    private final EmpleadoRepository empleadoRepository;

    public IncapacidadAdapter(IncapacidadRepository repository, ObjectMapper mapper, EmpleadoRepository empleadoRepository) {
        super(repository, mapper, d -> mapper.map(d, Incapacidad.class));
        this.empleadoRepository = empleadoRepository;
    }

    private Mono<Incapacidad> enrichIncapacidad(IncapacidadData d) {
        if (d == null) {
            return Mono.empty();
        }
        Incapacidad inc = mapper.map(d, Incapacidad.class);
        if (d.getEmpleadoCedula() != null) {
            return empleadoRepository.findById(d.getEmpleadoCedula())
                    .map(e -> mapper.map(e, Empleado.class))
                    .map(emp -> {
                        inc.setEmpleado(emp);
                        return inc;
                    })
                    .defaultIfEmpty(inc);
        }
        return Mono.just(inc);
    }

    @Override
    public Mono<Incapacidad> findById(String id) {
        return repository.findById(id).flatMap(this::enrichIncapacidad);
    }

    @Override
    public Flux<Incapacidad> findAll() {
        return repository.findAll().flatMap(this::enrichIncapacidad);
    }

    @Override
    public Mono<Incapacidad> save(Incapacidad entity) {
        IncapacidadData data = toData(entity);
        return saveData(data).flatMap(this::enrichIncapacidad);
    }

    @Override
    protected IncapacidadData toData(Incapacidad entity) {
        IncapacidadData data = super.toData(entity);
        if (data != null && entity != null) {
            if (entity.getEmpleado() != null && entity.getEmpleado().getCedula() != null) {
                data.setEmpleadoCedula(entity.getEmpleado().getCedula());
            }
        }
        return data;
    }

    @Override
    protected Mono<IncapacidadData> saveData(IncapacidadData data) {
        if (data != null) {
            if (data.getIdIncapacidad() == null || data.getIdIncapacidad().trim().isEmpty()) {
                data.setIdIncapacidad("INC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                data.setNew(true);
                return repository.save(data);
            }
            return repository.existsById(data.getIdIncapacidad())
                    .flatMap(exists -> {
                        data.setNew(!Boolean.TRUE.equals(exists));
                        return repository.save(data);
                    });
        }
        return super.saveData(data);
    }
}
