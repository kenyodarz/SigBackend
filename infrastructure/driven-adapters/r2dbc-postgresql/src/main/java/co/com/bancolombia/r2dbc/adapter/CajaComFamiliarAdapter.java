package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.gateways.CajaComFamiliarGateway;
import co.com.bancolombia.model.integrations.CajaComFamiliar;
import co.com.bancolombia.r2dbc.data.CajaComFamiliarData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.CajaComFamiliarRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CajaComFamiliarAdapter extends ReactiveAdapterOperations<CajaComFamiliar, CajaComFamiliarData, String, CajaComFamiliarRepository> implements CajaComFamiliarGateway {

    public CajaComFamiliarAdapter(CajaComFamiliarRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, CajaComFamiliar.class));
    }
}
