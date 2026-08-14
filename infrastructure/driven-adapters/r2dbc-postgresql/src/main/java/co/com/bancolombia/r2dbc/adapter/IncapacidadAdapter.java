package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.Incapacidad;
import co.com.bancolombia.model.gateways.IncapacidadGateway;
import co.com.bancolombia.model.integrations.CIE10;
import co.com.bancolombia.r2dbc.data.IncapacidadData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.CIE10Repository;
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
    private final CIE10Repository cie10Repository;

    public IncapacidadAdapter(IncapacidadRepository repository, ObjectMapper mapper, EmpleadoRepository empleadoRepository, CIE10Repository cie10Repository) {
        super(repository, mapper, d -> mapper.map(d, Incapacidad.class));
        this.empleadoRepository = empleadoRepository;
        this.cie10Repository = cie10Repository;
    }

    private Mono<Incapacidad> enrichIncapacidad(IncapacidadData d) {
        if (d == null) {
            return Mono.empty();
        }
        Incapacidad inc = mapper.map(d, Incapacidad.class);

        Mono<Incapacidad> withEmpleado = Mono.just(inc);
        if (d.getEmpleadoCedula() != null) {
            withEmpleado = empleadoRepository.findById(d.getEmpleadoCedula())
                    .map(e -> mapper.map(e, Empleado.class))
                    .map(emp -> {
                        inc.setEmpleado(emp);
                        return inc;
                    })
                    .defaultIfEmpty(inc);
        }

        return withEmpleado.flatMap(incResult -> {
            if (d.getCie10Codigo() != null) {
                return cie10Repository.findById(d.getCie10Codigo())
                        .map(c -> mapper.map(c, CIE10.class))
                        .map(cie -> {
                            incResult.setCie10(cie);
                            return incResult;
                        })
                        .defaultIfEmpty(incResult);
            }
            return Mono.just(incResult);
        });
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
            if (entity.getCie10() != null && entity.getCie10().getCodigo() != null) {
                data.setCie10Codigo(entity.getCie10().getCodigo());
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
