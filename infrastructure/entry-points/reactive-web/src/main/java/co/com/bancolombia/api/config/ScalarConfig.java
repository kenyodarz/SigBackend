package co.com.bancolombia.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ScalarConfig {

    private static final String SCALAR_HTML = """
                <!doctype html>
                <html>
                  <head>
                    <title>API Reference - Sig-Backend</title>
                    <meta charset="utf-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1" />
                  </head>
                  <body>
                    <script
                      id="api-reference"
                      data-url="/v3/api-docs"></script>
                    <script src="https://cdn.jsdelivr.net/npm/@scalar/api-reference"></script>
                  </body>
                </html>
                """;

    @Bean
    public RouterFunction<ServerResponse> scalarRouter() {
        return route(GET("/scalar"), request ->
                ServerResponse.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .header("Content-Security-Policy", "default-src 'self' 'unsafe-inline' 'unsafe-eval' https: data:;")
                        .bodyValue(SCALAR_HTML)
        ).andRoute(GET("/docs"), request ->
                ServerResponse.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .header("Content-Security-Policy", "default-src 'self' 'unsafe-inline' 'unsafe-eval' https: data:;")
                        .bodyValue(SCALAR_HTML)
        );
    }
}
