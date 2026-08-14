package co.com.bancolombia.api;

import co.com.bancolombia.api.handler.AuthHandler;
import co.com.bancolombia.api.handler.CapacitacionHandler;
import co.com.bancolombia.api.handler.ContratoHandler;
import co.com.bancolombia.api.handler.DocumentoHandler;
import co.com.bancolombia.api.handler.EmpleadoHandler;
import co.com.bancolombia.api.handler.EntregaDyEHandler;
import co.com.bancolombia.api.handler.ExamenHandler;
import co.com.bancolombia.api.handler.IncapacidadHandler;
import co.com.bancolombia.api.handler.RecommendationHandler;
import co.com.bancolombia.api.handler.VacacionesHandler;
import co.com.bancolombia.api.security.JwtUtils;
import co.com.bancolombia.model.Empleado;
import co.com.bancolombia.usecase.EmpleadoUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {
        RouterRest.class,
        EmpleadoHandler.class,
        AuthHandler.class,
        CapacitacionHandler.class,
        ContratoHandler.class,
        DocumentoHandler.class,
        EntregaDyEHandler.class,
        ExamenHandler.class,
        IncapacidadHandler.class,
        RecommendationHandler.class,
        VacacionesHandler.class
})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private JwtUtils jwtUtils;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private EmpleadoUseCase empleadoUseCase;

    @MockitoBean
    private co.com.bancolombia.usecase.UserUseCase userUseCase;

    @MockitoBean
    private co.com.bancolombia.usecase.RoleUseCase roleUseCase;

    @MockitoBean
    private co.com.bancolombia.usecase.CapacitacionUseCase capacitacionUseCase;

    @MockitoBean
    private co.com.bancolombia.usecase.ContratoUseCase contratoUseCase;

    @MockitoBean
    private co.com.bancolombia.usecase.DocumentoUseCase documentoUseCase;

    @MockitoBean
    private co.com.bancolombia.usecase.EntregaDyEUseCase entregaDyEUseCase;

    @MockitoBean
    private co.com.bancolombia.usecase.ExamenUseCase examenUseCase;

    @MockitoBean
    private co.com.bancolombia.usecase.IncapacidadUseCase incapacidadUseCase;

    @MockitoBean
    private co.com.bancolombia.usecase.RecommendationUseCase recommendationUseCase;

    @MockitoBean
    private co.com.bancolombia.usecase.VacacionesUseCase vacacionesUseCase;

    @Test
    void shouldReturnEmpleadosListWhenGetEmpleados() {
        // GIVEN
        Empleado empleado = Empleado.builder()
                .cedula("123456")
                .nombres("Carlos")
                .apellidos("Gomez")
                .build();

        when(empleadoUseCase.findAll()).thenReturn(Flux.just(empleado));

        // WHEN & THEN
        webTestClient.get()
                .uri("/api/empleados")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Empleado.class)
                .hasSize(1);
    }
}
