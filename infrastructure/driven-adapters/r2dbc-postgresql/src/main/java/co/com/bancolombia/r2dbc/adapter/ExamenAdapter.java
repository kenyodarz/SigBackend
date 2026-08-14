package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Examen;
import co.com.bancolombia.model.gateways.ExamenGateway;
import co.com.bancolombia.r2dbc.data.ExamenData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.ExamenRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ExamenAdapter extends ReactiveAdapterOperations<Examen, ExamenData, String, ExamenRepository> implements ExamenGateway {

    public ExamenAdapter(ExamenRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Examen.class));
    }
}
