package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.dto.LoginRequest;
import co.com.bancolombia.api.dto.MessageResponse;
import co.com.bancolombia.api.dto.SignupRequest;
import co.com.bancolombia.model.User;
import co.com.bancolombia.usecase.RoleUseCase;
import co.com.bancolombia.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final UserUseCase userUseCase;
    private final RoleUseCase roleUseCase;

    public Mono<ServerResponse> signin(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(login -> userUseCase.findByUsername(login.getUsername())
                        .flatMap(user -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(user))
                        .switchIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED)
                                .bodyValue(new MessageResponse("Error: Usuario o clave incorrecta"))));
    }

    public Mono<ServerResponse> signup(ServerRequest request) {
        return request.bodyToMono(SignupRequest.class)
                .flatMap(signup -> userUseCase.existsByUsername(signup.getUsername())
                        .flatMap(existsUsername -> {
                            if (Boolean.TRUE.equals(existsUsername)) {
                                return ServerResponse.badRequest()
                                        .bodyValue(new MessageResponse("Error: ¡El nombre de usuario ya existe!"));
                            }
                            return userUseCase.existsByEmail(signup.getEmail())
                                    .flatMap(existsEmail -> {
                                        if (Boolean.TRUE.equals(existsEmail)) {
                                            return ServerResponse.badRequest()
                                                    .bodyValue(new MessageResponse("Error: ¡El correo electrónico ya existe!"));
                                        }
                                        User newUser = User.builder()
                                                .id(UUID.randomUUID().toString())
                                                .username(signup.getUsername())
                                                .name(signup.getName())
                                                .email(signup.getEmail())
                                                .password(signup.getPassword())
                                                .build();
                                        return userUseCase.save(newUser)
                                                .flatMap(created -> ServerResponse.ok()
                                                        .bodyValue(new MessageResponse("¡Usuario registrado exitosamente!")));
                                    });
                        }));
    }
}
