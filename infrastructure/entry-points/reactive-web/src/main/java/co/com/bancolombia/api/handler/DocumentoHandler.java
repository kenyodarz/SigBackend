package co.com.bancolombia.api.handler;

import co.com.bancolombia.model.Documento;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.usecase.DocumentoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class DocumentoHandler {

    private final DocumentoUseCase useCase;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(useCase.findAll(), Documento.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCase.findById(id)
                .flatMap(entity -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(entity))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findByEmpleado(ServerRequest request) {
        String cedula = request.pathVariable("cedula");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(useCase.findByEmpleadoCedula(cedula), Documento.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Documento.class)
                .flatMap(useCase::save)
                .flatMap(saved -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(saved));
    }

    public Mono<ServerResponse> saveFile(ServerRequest request) {
        String cedula = request.pathVariable("cedula");
        return request.multipartData()
                .flatMap(partsMap -> {
                    Part archivoPart = partsMap.getFirst("archivo");
                    Part tipoPart = partsMap.getFirst("tipo");
                    Part nombrePart = partsMap.getFirst("nombre");

                    String tipo = (tipoPart instanceof FormFieldPart) ? ((FormFieldPart) tipoPart).value() : null;
                    String nombre = (nombrePart instanceof FormFieldPart) ? ((FormFieldPart) nombrePart).value() : null;

                    if (archivoPart == null || !(archivoPart instanceof FilePart)) {
                        return ServerResponse.badRequest().bodyValue("Archivo PDF faltante");
                    }

                    FilePart filePart = (FilePart) archivoPart;

                    return DataBufferUtils.join(filePart.content())
                            .map(dataBuffer -> {
                                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(bytes);
                                DataBufferUtils.release(dataBuffer);
                                return bytes;
                            })
                            .flatMap(bytes -> {
                                Empleado emp = Empleado.builder().cedula(cedula).build();
                                Documento doc = Documento.builder()
                                        .empleado(emp)
                                        .tipo(tipo)
                                        .nombre(nombre)
                                        .createAt(new Date())
                                        .archivo(bytes)
                                        .build();
                                return useCase.save(doc);
                            })
                            .flatMap(savedDoc -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(savedDoc));
                });
    }

    public Mono<ServerResponse> downloadPdf(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCase.findById(id)
                .flatMap(doc -> {
                    if (doc.getArchivo() != null && doc.getArchivo().length > 0) {
                        return ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_PDF)
                                .header("Content-Disposition", "inline; filename=\"" + (doc.getNombre() != null ? doc.getNombre() : "documento") + ".pdf\"")
                                .bodyValue(doc.getArchivo());
                    }
                    return ServerResponse.notFound().build();
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCase.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
