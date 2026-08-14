package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.EntregaDyE;
import co.com.bancolombia.model.gateways.EntregaDyEGateway;
import co.com.bancolombia.r2dbc.data.EntregaDyEData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.EntregaDyERepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EntregaDyEAdapter extends ReactiveAdapterOperations<EntregaDyE, EntregaDyEData, String, EntregaDyERepository> implements EntregaDyEGateway {

    public EntregaDyEAdapter(EntregaDyERepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, EntregaDyE.class));
    }
}
