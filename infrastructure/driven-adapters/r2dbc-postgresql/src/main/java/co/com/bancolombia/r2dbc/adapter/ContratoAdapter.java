package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Contrato;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.gateways.ContratoGateway;
import co.com.bancolombia.r2dbc.data.ContratoData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.ContratoRepository;
import co.com.bancolombia.r2dbc.repository.EmpleadoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class ContratoAdapter extends ReactiveAdapterOperations<Contrato, ContratoData, String, ContratoRepository> implements ContratoGateway {

    private final EmpleadoRepository empleadoRepository;

    public ContratoAdapter(ContratoRepository repository, ObjectMapper mapper, EmpleadoRepository empleadoRepository) {
        super(repository, mapper, d -> mapper.map(d, Contrato.class));
        this.empleadoRepository = empleadoRepository;
    }

    private Mono<Contrato> enrichContrato(ContratoData d) {
        if (d == null) {
            return Mono.empty();
        }
        Contrato contrato = mapper.map(d, Contrato.class);
        if (d.getEmpleadoCedula() != null) {
            return empleadoRepository.findById(d.getEmpleadoCedula())
                    .map(e -> mapper.map(e, Empleado.class))
                    .map(emp -> {
                        contrato.setEmpleado(emp);
                        return contrato;
                    })
                    .defaultIfEmpty(contrato);
        }
        return Mono.just(contrato);
    }

    @Override
    public Mono<Contrato> findById(String id) {
        return repository.findById(id).flatMap(this::enrichContrato);
    }

    @Override
    public Flux<Contrato> findAll() {
        return repository.findAll().flatMap(this::enrichContrato);
    }

    @Override
    public Mono<Contrato> save(Contrato entity) {
        ContratoData data = toData(entity);
        return saveData(data).flatMap(this::enrichContrato);
    }

    @Override
    protected ContratoData toData(Contrato entity) {
        ContratoData data = super.toData(entity);
        if (data != null && entity != null) {
            if (entity.getEmpleado() != null && entity.getEmpleado().getCedula() != null) {
                data.setEmpleadoCedula(entity.getEmpleado().getCedula());
            }
        }
        return data;
    }

    @Override
    protected Mono<ContratoData> saveData(ContratoData data) {
        if (data != null) {
            if (data.getIdContrato() == null || data.getIdContrato().trim().isEmpty()) {
                data.setIdContrato("CTR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                data.setNew(true);
                return repository.save(data);
            }
            return repository.existsById(data.getIdContrato())
                    .flatMap(exists -> {
                        data.setNew(!Boolean.TRUE.equals(exists));
                        return repository.save(data);
                    });
        }
        return super.saveData(data);
    }
}
