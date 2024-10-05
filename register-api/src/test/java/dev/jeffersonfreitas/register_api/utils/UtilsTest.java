package dev.jeffersonfreitas.register_api.utils;

import dev.jeffersonfreitas.register_api.exceptions.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@ActiveProfiles("tst")
@ExtendWith(SpringExtension.class)
class UtilsTest {

    @Test
    @DisplayName("Deverá ocorrer erro, pois o password é nulo ou vazio")
    void encryptPassword_PasswordInvalid() {
        final var errMessage = "A senha não pode ser nula ou vazia";

        final var resultEmpty = catchException(() -> Utils.encryptPassword(""));
        assertThat(resultEmpty).isInstanceOf(BadRequestException.class).hasMessage(errMessage);

        final var resultEmptySpace = catchException(() -> Utils.encryptPassword(" "));
        assertThat(resultEmptySpace).isInstanceOf(BadRequestException.class).hasMessage(errMessage);

        final var result = catchException(() -> Utils.encryptPassword(null));
        assertThat(result).isInstanceOf(BadRequestException.class).hasMessage(errMessage);
    }


    @Test
    @DisplayName("Deverá encriptar uma senha com sucesso")
    void encryptPassword_PasswordEncryptedSucessfully() {
        final var password = "abcABC123";

        final var result= Utils.encryptPassword(password);
        assertThat(result)
                .isNotNull()
                .isNotBlank()
                .isNotEqualTo(password);
    }
}
