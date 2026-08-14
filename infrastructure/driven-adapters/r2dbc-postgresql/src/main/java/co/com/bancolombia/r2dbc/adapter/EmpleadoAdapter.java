package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.gateways.EmpleadoGateway;
import co.com.bancolombia.r2dbc.data.EmpleadoData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.EmpleadoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class EmpleadoAdapter extends ReactiveAdapterOperations<Empleado, EmpleadoData, String, EmpleadoRepository> implements EmpleadoGateway {

    public EmpleadoAdapter(EmpleadoRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Empleado.class));
    }

    @Override
    protected EmpleadoData toData(Empleado entity) {
        EmpleadoData data = super.toData(entity);
        if (data != null && entity != null) {
            if (entity.getEps() != null) {
                data.setEpsNit(entity.getEps().getNit());
            }
            if (entity.getAfp() != null) {
                data.setAfpNit(entity.getAfp().getNit());
            }
            if (entity.getArl() != null) {
                data.setArlNit(entity.getArl().getNit());
            }
            if (entity.getCajaComFamiliar() != null) {
                data.setCajaComFamiliarNit(entity.getCajaComFamiliar().getNit());
            }
        }
        return data;
    }

    @Override
    protected Mono<EmpleadoData> saveData(EmpleadoData data) {
        if (data != null && data.getCedula() != null) {
            return repository.existsById(data.getCedula())
                    .flatMap(exists -> {
                        data.setNew(!Boolean.TRUE.equals(exists));
                        return repository.save(data);
                    });
        }
        return super.saveData(data);
    }
}
