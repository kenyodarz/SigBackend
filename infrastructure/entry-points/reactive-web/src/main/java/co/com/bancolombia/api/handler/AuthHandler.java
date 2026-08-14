package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.dto.JwtResponse;
import co.com.bancolombia.api.dto.LoginRequest;
import co.com.bancolombia.api.dto.MessageResponse;
import co.com.bancolombia.api.dto.SignupRequest;
import co.com.bancolombia.api.security.JwtUtils;
import co.com.bancolombia.model.Role;
import co.com.bancolombia.model.User;
import co.com.bancolombia.model.integrations.ERole;
import co.com.bancolombia.usecase.RoleUseCase;
import co.com.bancolombia.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final UserUseCase userUseCase;
    private final RoleUseCase roleUseCase;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public Mono<ServerResponse> signin(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(login -> userUseCase.findByUsername(login.getUsername())
                        .flatMap(user -> {
                            if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                                return ServerResponse.status(HttpStatus.UNAUTHORIZED)
                                        .bodyValue(new MessageResponse("Error: Contraseña incorrecta"));
                            }
                            String token = jwtUtils.generateToken(user.getUsername());
                            List<String> roles = user.getRoles() != null ? user.getRoles().stream()
                                    .map(r -> r.getName().name())
                                    .collect(Collectors.toList()) : Collections.emptyList();
                            JwtResponse response = JwtResponse.builder()
                                    .token(token)
                                    .type("Bearer")
                                    .id(user.getId())
                                    .username(user.getUsername())
                                    .name(user.getName())
                                    .email(user.getEmail())
                                    .roles(roles)
                                    .build();
                            return ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(response);
                        })
                        .switchIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED)
                                .bodyValue(new MessageResponse("Error: Usuario no encontrado"))));
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
                                        return resolveRoles(signup.getRole())
                                                .flatMap(roles -> {
                                                    User newUser = User.builder()
                                                            .id(UUID.randomUUID().toString())
                                                            .username(signup.getUsername())
                                                            .name(signup.getName())
                                                            .email(signup.getEmail())
                                                            .password(passwordEncoder.encode(signup.getPassword()))
                                                            .roles(roles)
                                                            .build();
                                                    return userUseCase.save(newUser)
                                                            .flatMap(created -> ServerResponse.ok()
                                                                    .bodyValue(new MessageResponse("¡Usuario registrado exitosamente!")));
                                                });
                                    });
                        }));
    }

    private Mono<Set<Role>> resolveRoles(Set<String> strRoles) {
        if (strRoles == null || strRoles.isEmpty()) {
            return roleUseCase.findByName(ERole.ROLE_USER)
                    .map(Set::of)
                    .defaultIfEmpty(Collections.emptySet());
        }
        return Flux.fromIterable(strRoles)
                .flatMap(roleName -> {
                    ERole eRole;
                    switch (roleName.toLowerCase()) {
                        case "admin":
                        case "role_admin":
                            eRole = ERole.ROLE_ADMIN;
                            break;
                        case "mod":
                        case "moderator":
                        case "role_moderator":
                            eRole = ERole.ROLE_MODERATOR;
                            break;
                        case "supervisor":
                        case "role_supervisor":
                            eRole = ERole.ROLE_SUPERVISOR;
                            break;
                        default:
                            eRole = ERole.ROLE_USER;
                            break;
                    }
                    return roleUseCase.findByName(eRole);
                })
                .collect(Collectors.toSet());
    }
}
