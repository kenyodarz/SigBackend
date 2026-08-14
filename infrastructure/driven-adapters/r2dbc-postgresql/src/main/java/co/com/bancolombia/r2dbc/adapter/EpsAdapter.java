package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.gateways.EpsGateway;
import co.com.bancolombia.model.integrations.Eps;
import co.com.bancolombia.r2dbc.data.EpsData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.EpsRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EpsAdapter extends ReactiveAdapterOperations<Eps, EpsData, String, EpsRepository> implements EpsGateway {

    public EpsAdapter(EpsRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Eps.class));
    }
}
