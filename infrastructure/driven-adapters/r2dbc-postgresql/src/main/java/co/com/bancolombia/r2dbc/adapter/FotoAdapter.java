package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Foto;
import co.com.bancolombia.model.gateways.FotoGateway;
import co.com.bancolombia.r2dbc.data.FotoData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.FotoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class FotoAdapter extends ReactiveAdapterOperations<Foto, FotoData, String, FotoRepository> implements FotoGateway {

    public FotoAdapter(FotoRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Foto.class));
    }

    @Override
    protected Mono<FotoData> saveData(FotoData data) {
        if (data != null && data.getIdFoto() != null) {
            return repository.existsById(data.getIdFoto())
                    .flatMap(exists -> {
                        data.setNew(!Boolean.TRUE.equals(exists));
                        return repository.save(data);
                    });
        }
        return super.saveData(data);
    }
}
