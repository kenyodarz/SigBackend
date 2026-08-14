package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Vacaciones;
import co.com.bancolombia.model.gateways.VacacionesGateway;
import co.com.bancolombia.r2dbc.data.VacacionesData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.VacacionesRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class VacacionesAdapter extends ReactiveAdapterOperations<Vacaciones, VacacionesData, String, VacacionesRepository> implements VacacionesGateway {

    public VacacionesAdapter(VacacionesRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Vacaciones.class));
    }
}
