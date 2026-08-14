package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.gateways.EmpleadoGateway;
import co.com.bancolombia.model.integrations.Afp;
import co.com.bancolombia.model.integrations.Arl;
import co.com.bancolombia.model.integrations.CajaComFamiliar;
import co.com.bancolombia.model.integrations.Eps;
import co.com.bancolombia.r2dbc.data.EmpleadoData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.AfpRepository;
import co.com.bancolombia.r2dbc.repository.ArlRepository;
import co.com.bancolombia.r2dbc.repository.CajaComFamiliarRepository;
import co.com.bancolombia.r2dbc.repository.EmpleadoRepository;
import co.com.bancolombia.r2dbc.repository.EpsRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public class EmpleadoAdapter extends ReactiveAdapterOperations<Empleado, EmpleadoData, String, EmpleadoRepository> implements EmpleadoGateway {

    private final EpsRepository epsRepository;
    private final AfpRepository afpRepository;
    private final ArlRepository arlRepository;
    private final CajaComFamiliarRepository cajaRepository;

    public EmpleadoAdapter(
            EmpleadoRepository repository,
            ObjectMapper mapper,
            EpsRepository epsRepository,
            AfpRepository afpRepository,
            ArlRepository arlRepository,
            CajaComFamiliarRepository cajaRepository
    ) {
        super(repository, mapper, d -> mapper.map(d, Empleado.class));
        this.epsRepository = epsRepository;
        this.afpRepository = afpRepository;
        this.arlRepository = arlRepository;
        this.cajaRepository = cajaRepository;
    }

    private Mono<Empleado> enrichEmpleado(EmpleadoData d) {
        if (d == null) {
            return Mono.empty();
        }
        Empleado empleado = mapper.map(d, Empleado.class);

        Mono<Optional<Eps>> epsMono = d.getEpsNit() != null
                ? epsRepository.findById(d.getEpsNit()).map(e -> mapper.map(e, Eps.class)).map(Optional::of).defaultIfEmpty(Optional.empty())
                : Mono.just(Optional.empty());

        Mono<Optional<Afp>> afpMono = d.getAfpNit() != null
                ? afpRepository.findById(d.getAfpNit()).map(a -> mapper.map(a, Afp.class)).map(Optional::of).defaultIfEmpty(Optional.empty())
                : Mono.just(Optional.empty());

        Mono<Optional<Arl>> arlMono = d.getArlNit() != null
                ? arlRepository.findById(d.getArlNit()).map(a -> mapper.map(a, Arl.class)).map(Optional::of).defaultIfEmpty(Optional.empty())
                : Mono.just(Optional.empty());

        Mono<Optional<CajaComFamiliar>> cajaMono = d.getCajaComFamiliarNit() != null
                ? cajaRepository.findById(d.getCajaComFamiliarNit()).map(c -> mapper.map(c, CajaComFamiliar.class)).map(Optional::of).defaultIfEmpty(Optional.empty())
                : Mono.just(Optional.empty());

        return Mono.zip(epsMono, afpMono, arlMono, cajaMono)
                .map(tuple -> {
                    tuple.getT1().ifPresent(empleado::setEps);
                    tuple.getT2().ifPresent(empleado::setAfp);
                    tuple.getT3().ifPresent(empleado::setArl);
                    tuple.getT4().ifPresent(empleado::setCajaComFamiliar);
                    return empleado;
                });
    }

    @Override
    public Mono<Empleado> findById(String id) {
        return repository.findById(id).flatMap(this::enrichEmpleado);
    }

    @Override
    public Flux<Empleado> findAll() {
        return repository.findAll().flatMap(this::enrichEmpleado);
    }

    @Override
    public Mono<Empleado> save(Empleado entity) {
        EmpleadoData data = toData(entity);
        return saveData(data).flatMap(this::enrichEmpleado);
    }

    @Override
    protected EmpleadoData toData(Empleado entity) {
        EmpleadoData data = super.toData(entity);
        if (data != null && entity != null) {
            if (entity.getEps() != null) {
                data.setEpsNit(entity.getEps().getNit());
            }
            if (entity.getAfp() != null) {
                data.setAfpNit(entity.getAfp().getNit());
            }
            if (entity.getArl() != null) {
                data.setArlNit(entity.getArl().getNit());
            }
            if (entity.getCajaComFamiliar() != null) {
                data.setCajaComFamiliarNit(entity.getCajaComFamiliar().getNit());
            }
        }
        return data;
    }

    @Override
    protected Mono<EmpleadoData> saveData(EmpleadoData data) {
        if (data != null && data.getCedula() != null) {
            return repository.existsById(data.getCedula())
                    .flatMap(exists -> {
                        data.setNew(!Boolean.TRUE.equals(exists));
                        return repository.save(data);
                    });
        }
        return super.saveData(data);
    }
}
