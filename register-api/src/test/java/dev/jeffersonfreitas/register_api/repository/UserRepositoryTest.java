package dev.jeffersonfreitas.register_api.repository;

import dev.jeffersonfreitas.register_api.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("tst")
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("Deverá retornar um optional vazio quando não encontrar o usuário pelo e-mail")
    void findByEmailIgnoreCase_NotFoundByEmail() {
        final var email = "fulano@gmail.com";
        final var result = repository.findByEmailIgnoreCase(email);

        assertThat(result).isNotNull().isEmpty();
    }


    @Test
    @DisplayName("Deverá retornar um usuário com o email informado")
    void findByEmailIgnoreCase_EmailFoundedLowerCase() {
        final var uuid = UUID.randomUUID().toString();
        final var name = "Fulano da Silva";
        final var email = "fulano@gmail.com";
        final var password = "abcABC123";
        final var user = new User(uuid, name, email, password);

        entityManager.persist(user);

        final var result = repository.findByEmailIgnoreCase(email);

        assertThat(result).isNotNull().isPresent();
        assertThat(result.get().getName()).isEqualTo(name);
        assertThat(result.get().getUuid()).isEqualTo(uuid);
        assertThat(result.get().getEmail()).isEqualTo(email);
    }


    @Test
    @DisplayName("Deverá retornar um usuário com o email informado idependente se está em lowercase")
    void findByEmailIgnoreCase_EmailFoundedNotOnlyLowerCase() {
        final var uuid = UUID.randomUUID().toString();
        final var name = "Fulano da Silva";
        final var email = "Fulano@GMAIL.com";
        final var password = "abcABC123";
        final var user = new User(uuid, name, email.toLowerCase(), password);

        entityManager.persist(user);

        final var result = repository.findByEmailIgnoreCase(email);

        assertThat(result).isNotNull().isPresent();
        assertThat(result.get().getName()).isEqualTo(name);
        assertThat(result.get().getUuid()).isEqualTo(uuid);
        assertThat(result.get().getEmail()).isEqualTo(email.toLowerCase());
    }
}
