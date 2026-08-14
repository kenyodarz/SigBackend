package co.com.bancolombia.api.handler;

import co.com.bancolombia.model.Foto;
import co.com.bancolombia.usecase.FotoUseCase;
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

@Component
@RequiredArgsConstructor
public class FotoHandler {

    private final FotoUseCase fotoUseCase;

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.multipartData()
                .flatMap(partsMap -> {
                    Part idFotoPart = partsMap.getFirst("idFoto");
                    Part archivoPart = partsMap.getFirst("archivo");

                    String idFoto = (idFotoPart instanceof FormFieldPart) ? ((FormFieldPart) idFotoPart).value() : null;

                    if (idFoto == null || archivoPart == null || !(archivoPart instanceof FilePart)) {
                        return ServerResponse.badRequest().bodyValue("Parámetros idFoto o archivo faltantes");
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
                                Foto foto = Foto.builder()
                                        .idFoto(idFoto)
                                        .foto(bytes)
                                        .build();
                                return fotoUseCase.save(foto);
                            })
                            .flatMap(savedFoto -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(savedFoto));
                });
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return fotoUseCase.findById(id)
                .flatMap(foto -> {
                    if (foto.getFoto() != null && foto.getFoto().length > 0) {
                        return ServerResponse.ok()
                                .contentType(MediaType.IMAGE_PNG)
                                .bodyValue(foto.getFoto());
                    }
                    return ServerResponse.notFound().build();
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
