package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.gateways.EmpleadoGateway;
import co.com.bancolombia.r2dbc.data.EmpleadoData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.EmpleadoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EmpleadoAdapter extends ReactiveAdapterOperations<Empleado, EmpleadoData, String, EmpleadoRepository> implements EmpleadoGateway {

    public EmpleadoAdapter(EmpleadoRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Empleado.class));
    }
}
