package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.User;
import co.com.bancolombia.r2dbc.data.UserData;
import co.com.bancolombia.r2dbc.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ObjectMapper mapper;

    private UserAdapter userAdapter;

    @BeforeEach
    void setUp() {
        userAdapter = new UserAdapter(userRepository, mapper);
    }

    @Test
    void shouldFindUserByIdWhenUserExists() {
        // GIVEN
        String id = "user-123";
        UserData userData = UserData.builder().id(id).username("john").email("john@example.com").build();
        User userModel = User.builder().id(id).username("john").email("john@example.com").build();

        when(userRepository.findById(id)).thenReturn(Mono.just(userData));
        when(mapper.map(any(), eq(User.class))).thenReturn(userModel);

        // WHEN
        Mono<User> result = userAdapter.findById(id);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(user -> user.getUsername().equals("john"))
                .verifyComplete();
    }
}
