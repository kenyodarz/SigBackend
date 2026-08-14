package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Documento;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.model.gateways.DocumentoGateway;
import co.com.bancolombia.r2dbc.data.DocumentoData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.DocumentoRepository;
import co.com.bancolombia.r2dbc.repository.EmpleadoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class DocumentoAdapter extends ReactiveAdapterOperations<Documento, DocumentoData, String, DocumentoRepository> implements DocumentoGateway {

    private final EmpleadoRepository empleadoRepository;

    public DocumentoAdapter(DocumentoRepository repository, ObjectMapper mapper, EmpleadoRepository empleadoRepository) {
        super(repository, mapper, d -> mapper.map(d, Documento.class));
        this.empleadoRepository = empleadoRepository;
    }

    private Mono<Documento> enrichDocumento(DocumentoData d) {
        if (d == null) {
            return Mono.empty();
        }
        Documento doc = mapper.map(d, Documento.class);
        if (d.getEmpleadoCedula() != null) {
            return empleadoRepository.findById(d.getEmpleadoCedula())
                    .map(e -> mapper.map(e, Empleado.class))
                    .map(emp -> {
                        doc.setEmpleado(emp);
                        return doc;
                    })
                    .defaultIfEmpty(doc);
        }
        return Mono.just(doc);
    }

    @Override
    public Mono<Documento> findById(String id) {
        return repository.findById(id).flatMap(this::enrichDocumento);
    }

    @Override
    public Flux<Documento> findAll() {
        return repository.findAll().flatMap(this::enrichDocumento);
    }

    @Override
    public Flux<Documento> findByEmpleadoCedula(String cedula) {
        return repository.findByEmpleadoCedula(cedula).flatMap(this::enrichDocumento);
    }

    @Override
    public Mono<Documento> save(Documento entity) {
        DocumentoData data = toData(entity);
        if (data.getCreateAt() == null) {
            data.setCreateAt(LocalDateTime.now());
        }
        return saveData(data).flatMap(this::enrichDocumento);
    }

    @Override
    protected DocumentoData toData(Documento entity) {
        DocumentoData data = super.toData(entity);
        if (data != null && entity != null) {
            if (entity.getEmpleado() != null && entity.getEmpleado().getCedula() != null) {
                data.setEmpleadoCedula(entity.getEmpleado().getCedula());
            }
        }
        return data;
    }

    @Override
    protected Mono<DocumentoData> saveData(DocumentoData data) {
        if (data != null) {
            if (data.getIdDocumento() == null || data.getIdDocumento().trim().isEmpty()) {
                data.setIdDocumento("DOC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                data.setNew(true);
                return repository.save(data);
            }
            return repository.existsById(data.getIdDocumento())
                    .flatMap(exists -> {
                        data.setNew(!Boolean.TRUE.equals(exists));
                        return repository.save(data);
                    });
        }
        return super.saveData(data);
    }
}
