package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.gateways.AfpGateway;
import co.com.bancolombia.model.integrations.Afp;
import co.com.bancolombia.r2dbc.data.AfpData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.AfpRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AfpAdapter extends ReactiveAdapterOperations<Afp, AfpData, String, AfpRepository> implements AfpGateway {

    public AfpAdapter(AfpRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Afp.class));
    }
}
