package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Foto;
import co.com.bancolombia.model.gateways.FotoGateway;
import co.com.bancolombia.r2dbc.data.FotoData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.FotoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FotoAdapter extends ReactiveAdapterOperations<Foto, FotoData, String, FotoRepository> implements FotoGateway {

    public FotoAdapter(FotoRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Foto.class));
    }
}
