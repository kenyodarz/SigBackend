package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.gateways.ArlGateway;
import co.com.bancolombia.model.integrations.Arl;
import co.com.bancolombia.r2dbc.data.ArlData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.ArlRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ArlAdapter extends ReactiveAdapterOperations<Arl, ArlData, String, ArlRepository> implements ArlGateway {

    public ArlAdapter(ArlRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Arl.class));
    }
}
