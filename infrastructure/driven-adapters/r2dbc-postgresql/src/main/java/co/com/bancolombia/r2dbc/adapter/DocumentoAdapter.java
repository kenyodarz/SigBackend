package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.Documento;
import co.com.bancolombia.model.gateways.DocumentoGateway;
import co.com.bancolombia.r2dbc.data.DocumentoData;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repository.DocumentoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentoAdapter extends ReactiveAdapterOperations<Documento, DocumentoData, String, DocumentoRepository> implements DocumentoGateway {

    public DocumentoAdapter(DocumentoRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Documento.class));
    }
}
