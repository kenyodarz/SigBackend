package co.com.bancolombia.api.config;

import jakarta.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;
import tools.jackson.databind.json.JsonMapper;

@Component
@Order(-2)
public class GlobalExceptionHandler implements WebExceptionHandler {

    private final JsonMapper jsonMapper;

    public GlobalExceptionHandler() {
        this.jsonMapper = new JsonMapper();
    }

    @Nonnull
    @Override
    public Mono<Void> handle(@Nonnull ServerWebExchange exchange, Throwable ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorName = "Internal Server Error";
        String message = ex.getMessage();

        if (ex instanceof IllegalArgumentException || ex instanceof IllegalStateException) {
            status = HttpStatus.BAD_REQUEST;
            errorName = "Bad Request";
        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> responseBody = Map.of(
                "status", status.value(),
                "error", errorName,
                "message", message != null ? message : "Solicitud inválida"
        );

        try {
            byte[] bytes = jsonMapper.writeValueAsBytes(responseBody);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception _) {
            byte[] fallback = ("{\"status\":" + status.value() + ",\"error\":\"" + errorName + "\",\"message\":\"" + message + "\"}").getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(fallback);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }
    }
}
