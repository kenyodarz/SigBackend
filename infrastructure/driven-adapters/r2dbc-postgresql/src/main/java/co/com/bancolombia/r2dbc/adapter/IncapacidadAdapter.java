package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Incapacidad;
import co.com.bancolombia.model.gateways.IncapacidadGateway;
import co.com.bancolombia.r2dbc.data.IncapacidadData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.IncapacidadRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class IncapacidadAdapter extends ReactiveAdapterOperations<Incapacidad, IncapacidadData, String, IncapacidadRepository> implements IncapacidadGateway {

    public IncapacidadAdapter(IncapacidadRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Incapacidad.class));
    }
}
