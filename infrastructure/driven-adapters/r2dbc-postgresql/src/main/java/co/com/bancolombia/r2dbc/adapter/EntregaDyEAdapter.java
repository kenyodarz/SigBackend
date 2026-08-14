package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.EntregaDyE;
import co.com.bancolombia.model.gateways.EntregaDyEGateway;
import co.com.bancolombia.r2dbc.data.EntregaDyEData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.EmpleadoRepository;
import co.com.bancolombia.r2dbc.repository.EntregaDyERepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class EntregaDyEAdapter extends ReactiveAdapterOperations<EntregaDyE, EntregaDyEData, String, EntregaDyERepository> implements EntregaDyEGateway {

    private final EmpleadoRepository empleadoRepository;

    public EntregaDyEAdapter(EntregaDyERepository repository, ObjectMapper mapper, EmpleadoRepository empleadoRepository) {
        super(repository, mapper, d -> mapper.map(d, EntregaDyE.class));
        this.empleadoRepository = empleadoRepository;
    }

    private Mono<EntregaDyE> enrichEntregaDyE(EntregaDyEData d) {
        if (d == null) {
            return Mono.empty();
        }
        EntregaDyE entrega = mapper.map(d, EntregaDyE.class);
        if (d.getEmpleadoCedula() != null) {
            return empleadoRepository.findById(d.getEmpleadoCedula())
                    .map(e -> mapper.map(e, Empleado.class))
                    .map(emp -> {
                        entrega.setEmpleado(emp);
                        return entrega;
                    })
                    .defaultIfEmpty(entrega);
        }
        return Mono.just(entrega);
    }

    @Override
    public Mono<EntregaDyE> findById(String id) {
        return repository.findById(id).flatMap(this::enrichEntregaDyE);
    }

    @Override
    public Flux<EntregaDyE> findAll() {
        return repository.findAll().flatMap(this::enrichEntregaDyE);
    }

    @Override
    public Mono<EntregaDyE> save(EntregaDyE entity) {
        EntregaDyEData data = toData(entity);
        return saveData(data).flatMap(this::enrichEntregaDyE);
    }

    @Override
    protected EntregaDyEData toData(EntregaDyE entity) {
        EntregaDyEData data = super.toData(entity);
        if (data != null && entity != null) {
            if (entity.getEmpleado() != null && entity.getEmpleado().getCedula() != null) {
                data.setEmpleadoCedula(entity.getEmpleado().getCedula());
            }
        }
        return data;
    }

    @Override
    protected Mono<EntregaDyEData> saveData(EntregaDyEData data) {
        if (data != null) {
            if (data.getIdEntregaDyE() == null || data.getIdEntregaDyE().trim().isEmpty()) {
                data.setIdEntregaDyE("ENT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                data.setNew(true);
                return repository.save(data);
            }
            return repository.existsById(data.getIdEntregaDyE())
                    .flatMap(exists -> {
                        data.setNew(!Boolean.TRUE.equals(exists));
                        return repository.save(data);
                    });
        }
        return super.saveData(data);
    }
}
