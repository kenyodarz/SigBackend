package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Documento;
import co.com.bancolombia.model.gateways.DocumentoGateway;
import co.com.bancolombia.r2dbc.data.DocumentoData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.DocumentoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class DocumentoAdapter extends ReactiveAdapterOperations<Documento, DocumentoData, String, DocumentoRepository> implements DocumentoGateway {

    public DocumentoAdapter(DocumentoRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Documento.class));
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
