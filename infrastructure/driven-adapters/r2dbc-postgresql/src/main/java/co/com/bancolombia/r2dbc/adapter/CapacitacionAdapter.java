package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Capacitacion;
import co.com.bancolombia.model.gateways.CapacitacionGateway;
import co.com.bancolombia.r2dbc.data.CapacitacionData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.CapacitacionRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class CapacitacionAdapter extends ReactiveAdapterOperations<Capacitacion, CapacitacionData, String, CapacitacionRepository> implements CapacitacionGateway {

    public CapacitacionAdapter(CapacitacionRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Capacitacion.class));
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
