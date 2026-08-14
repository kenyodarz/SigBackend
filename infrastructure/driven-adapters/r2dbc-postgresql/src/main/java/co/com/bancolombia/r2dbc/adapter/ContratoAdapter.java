package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Contrato;
import co.com.bancolombia.model.gateways.ContratoGateway;
import co.com.bancolombia.r2dbc.data.ContratoData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.ContratoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ContratoAdapter extends ReactiveAdapterOperations<Contrato, ContratoData, String, ContratoRepository> implements ContratoGateway {

    public ContratoAdapter(ContratoRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Contrato.class));
    }
}
