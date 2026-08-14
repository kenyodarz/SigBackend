package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.gateways.CIE10Gateway;
import co.com.bancolombia.model.integrations.CIE10;
import co.com.bancolombia.r2dbc.data.CIE10Data;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.CIE10Repository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CIE10Adapter extends ReactiveAdapterOperations<CIE10, CIE10Data, String, CIE10Repository> implements CIE10Gateway {

    public CIE10Adapter(CIE10Repository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, CIE10.class));
    }
}
