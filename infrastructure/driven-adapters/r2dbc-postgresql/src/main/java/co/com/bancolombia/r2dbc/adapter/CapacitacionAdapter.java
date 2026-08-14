package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Capacitacion;
import co.com.bancolombia.model.gateways.CapacitacionGateway;
import co.com.bancolombia.r2dbc.data.CapacitacionData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.CapacitacionRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CapacitacionAdapter extends ReactiveAdapterOperations<Capacitacion, CapacitacionData, String, CapacitacionRepository> implements CapacitacionGateway {

    public CapacitacionAdapter(CapacitacionRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Capacitacion.class));
    }
}
