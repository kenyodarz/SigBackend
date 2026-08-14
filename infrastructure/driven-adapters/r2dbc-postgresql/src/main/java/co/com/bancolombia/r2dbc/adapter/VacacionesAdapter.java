package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Contrato;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.Vacaciones;
import co.com.bancolombia.model.gateways.VacacionesGateway;
import co.com.bancolombia.r2dbc.data.VacacionesData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.ContratoRepository;
import co.com.bancolombia.r2dbc.repository.EmpleadoRepository;
import co.com.bancolombia.r2dbc.repository.VacacionesRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class VacacionesAdapter extends ReactiveAdapterOperations<Vacaciones, VacacionesData, String, VacacionesRepository> implements VacacionesGateway {

    private final ContratoRepository contratoRepository;
    private final EmpleadoRepository empleadoRepository;

    public VacacionesAdapter(VacacionesRepository repository, ObjectMapper mapper, ContratoRepository contratoRepository, EmpleadoRepository empleadoRepository) {
        super(repository, mapper, d -> mapper.map(d, Vacaciones.class));
        this.contratoRepository = contratoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    private Mono<Vacaciones> enrichVacaciones(VacacionesData d) {
        if (d == null) {
            return Mono.empty();
        }
        Vacaciones vac = mapper.map(d, Vacaciones.class);
        if (d.getContratoId() != null) {
            return contratoRepository.findById(d.getContratoId())
                    .flatMap(cData -> {
                        Contrato contrato = mapper.map(cData, Contrato.class);
                        if (cData.getEmpleadoCedula() != null) {
                            return empleadoRepository.findById(cData.getEmpleadoCedula())
                                    .map(e -> mapper.map(e, Empleado.class))
                                    .map(emp -> {
                                        contrato.setEmpleado(emp);
                                        vac.setContrato(contrato);
                                        return vac;
                                    })
                                    .defaultIfEmpty(vac);
                        }
                        vac.setContrato(contrato);
                        return Mono.just(vac);
                    })
                    .defaultIfEmpty(vac);
        }
        return Mono.just(vac);
    }

    @Override
    public Mono<Vacaciones> findById(String id) {
        return repository.findById(id).flatMap(this::enrichVacaciones);
    }

    @Override
    public Flux<Vacaciones> findAll() {
        return repository.findAll().flatMap(this::enrichVacaciones);
    }

    @Override
    public Mono<Vacaciones> save(Vacaciones entity) {
        VacacionesData data = toData(entity);
        return saveData(data).flatMap(this::enrichVacaciones);
    }

    @Override
    protected VacacionesData toData(Vacaciones entity) {
        VacacionesData data = super.toData(entity);
        if (data != null && entity != null) {
            if (entity.getContrato() != null && entity.getContrato().getIdContrato() != null) {
                data.setContratoId(entity.getContrato().getIdContrato());
            }
        }
        return data;
    }

    @Override
    protected Mono<VacacionesData> saveData(VacacionesData data) {
        if (data != null) {
            if (data.getIdVacaciones() == null || data.getIdVacaciones().trim().isEmpty()) {
                data.setIdVacaciones("VAC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                data.setNew(true);
                return repository.save(data);
            }
            return repository.existsById(data.getIdVacaciones())
                    .flatMap(exists -> {
                        data.setNew(!Boolean.TRUE.equals(exists));
                        return repository.save(data);
                    });
        }
        return super.saveData(data);
    }
}
