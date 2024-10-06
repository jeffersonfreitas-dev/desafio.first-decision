package dev.jeffersonfreitas.register_api.model;

import dev.jeffersonfreitas.register_api.dto.user.UserRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("tst")
@ExtendWith(SpringExtension.class)
class UserTest {

    @Test
    @DisplayName("Deverá criar um user a partir do request com sucesso")
    void create_CreateUserSuccessfully() {
        final var name = "Fulano da Silva";
        final var email = "fulano@gmail.com";
        final var password = "abcABC123";
        final var request = new UserRegisterRequest(name, email, password, password);

        final var result = User.create(request);

        assertThat(result).isNotNull();
        assertThat(result.getUuid()).isNotNull().isNotBlank();
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getEmail()).isEqualTo(email);
    }
}
