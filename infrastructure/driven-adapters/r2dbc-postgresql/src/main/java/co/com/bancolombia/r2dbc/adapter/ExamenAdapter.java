package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Contrato;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.Examen;
import co.com.bancolombia.model.gateways.ExamenGateway;
import co.com.bancolombia.r2dbc.data.ExamenData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.ContratoRepository;
import co.com.bancolombia.r2dbc.repository.EmpleadoRepository;
import co.com.bancolombia.r2dbc.repository.ExamenRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class ExamenAdapter extends ReactiveAdapterOperations<Examen, ExamenData, String, ExamenRepository> implements ExamenGateway {

    private final ContratoRepository contratoRepository;
    private final EmpleadoRepository empleadoRepository;

    public ExamenAdapter(ExamenRepository repository, ObjectMapper mapper, ContratoRepository contratoRepository, EmpleadoRepository empleadoRepository) {
        super(repository, mapper, d -> mapper.map(d, Examen.class));
        this.contratoRepository = contratoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    private Mono<Examen> enrichExamen(ExamenData d) {
        if (d == null) {
            return Mono.empty();
        }
        Examen examen = mapper.map(d, Examen.class);
        if (d.getContratoId() != null) {
            return contratoRepository.findById(d.getContratoId())
                    .flatMap(cData -> {
                        Contrato contrato = mapper.map(cData, Contrato.class);
                        if (cData.getEmpleadoCedula() != null) {
                            return empleadoRepository.findById(cData.getEmpleadoCedula())
                                    .map(e -> mapper.map(e, Empleado.class))
                                    .map(emp -> {
                                        contrato.setEmpleado(emp);
                                        examen.setContrato(contrato);
                                        return examen;
                                    })
                                    .defaultIfEmpty(examen);
                        }
                        examen.setContrato(contrato);
                        return Mono.just(examen);
                    })
                    .defaultIfEmpty(examen);
        }
        return Mono.just(examen);
    }

    @Override
    public Mono<Examen> findById(String id) {
        return repository.findById(id).flatMap(this::enrichExamen);
    }

    @Override
    public Flux<Examen> findAll() {
        return repository.findAll().flatMap(this::enrichExamen);
    }

    @Override
    public Mono<Examen> save(Examen entity) {
        ExamenData data = toData(entity);
        return saveData(data).flatMap(this::enrichExamen);
    }

    @Override
    protected ExamenData toData(Examen entity) {
        ExamenData data = super.toData(entity);
        if (data != null && entity != null) {
            if (entity.getContrato() != null && entity.getContrato().getIdContrato() != null) {
                data.setContratoId(entity.getContrato().getIdContrato());
            }
        }
        return data;
    }

    @Override
    protected Mono<ExamenData> saveData(ExamenData data) {
        if (data != null) {
            if (data.getIdExamen() == null || data.getIdExamen().trim().isEmpty()) {
                data.setIdExamen("EXM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                data.setNew(true);
                return repository.save(data);
            }
            return repository.existsById(data.getIdExamen())
                    .flatMap(exists -> {
                        data.setNew(!Boolean.TRUE.equals(exists));
                        return repository.save(data);
                    });
        }
        return super.saveData(data);
    }
}
