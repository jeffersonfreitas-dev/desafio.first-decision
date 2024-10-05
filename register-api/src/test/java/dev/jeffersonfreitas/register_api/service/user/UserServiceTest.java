package dev.jeffersonfreitas.register_api.service.user;

import dev.jeffersonfreitas.register_api.dto.user.UserRegisterRequest;
import dev.jeffersonfreitas.register_api.exceptions.AlreadyRegisteredException;
import dev.jeffersonfreitas.register_api.exceptions.BadRequestException;
import dev.jeffersonfreitas.register_api.exceptions.NotFoundException;
import dev.jeffersonfreitas.register_api.model.User;
import dev.jeffersonfreitas.register_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("tst")
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    private UserService service;
    @Mock private UserRepository repository;

    @BeforeEach
    void setup(){
        this.service = new UserServiceImpl(repository);
    }

    @Test
    @DisplayName("Deverá ocorrer um erro, pois o objeto de requisição está nulo")
    void create_ErrorNullRequestObject() {

        final var result = catchException(() -> service.create(null));
        assertThat(result).isInstanceOf(BadRequestException.class)
                .hasMessage("O objeto de requisição não pode ser nulo");

        verify(repository, never()).save(any());
    }


    @Test
    @DisplayName("Deverá ocorrer um erro quando a confirmação de senha for in´valida")
    void create_InvalidPasswordConfirmation() {
        final var name = "Fulano da Silva";
        final var email = "fulano@gmail.com";
        final var password = "abcABC123";
        final var passwordConfirmation = "abc";
        final var request = new UserRegisterRequest(name, email, password, passwordConfirmation);

        final var result = catchException(() -> service.create(request));
        assertThat(result).isInstanceOf(BadRequestException.class)
                .hasMessage("A confirmação de senha deverá ser igual a senha");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deverá ocorrer um erro quando já existir um usuário com o mesmo e-mail cadastrado")
    void create_EmailAlreadyRegistered() {
        final var name = "Fulano da Silva";
        final var email = "fulano@gmail.com";
        final var password = "abcABC123";
        final var request = new UserRegisterRequest(name, email, password, password);
        final var user = User.create(request);

        when(repository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));

        final var result = catchException(() -> service.create(request));
        assertThat(result).isInstanceOf(AlreadyRegisteredException.class)
                .hasMessage("Já existe um usuário com este e-mail cadastrado");

        verify(repository, never()).save(any());
        verify(repository, times(1)).findByEmailIgnoreCase(anyString());
    }


    @Test
    @DisplayName("Deverá salvar com sucesso no banco")
    void create_SaveSucessfully() {
        final var name = "Fulano da Silva";
        final var email = "fulano@gmail.com";
        final var password = "abcABC123";
        final var request = new UserRegisterRequest(name, email, password, password);

        when(repository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());

        final var result = service.create(request);

        assertThat(result).isNotNull();
        assertThat(result.uuid()).isNotNull().isNotBlank();
        assertThat(result.name()).isNotNull().isNotBlank().isEqualTo(name);
        assertThat(result.email()).isNotNull().isNotBlank().isEqualTo(email);

        verify(repository, times(1)).save(any());
        verify(repository, times(1)).findByEmailIgnoreCase(anyString());
    }


    @Test
    @DisplayName("Deverá retornar vazio quando não houver registros salvo no banco")
    void fetchUsers_ReturnEmpty() {

        when(repository.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(Collections.emptyList());
        final var result = service.fetchUsers();

        assertThat(result).isNotNull().isEmpty();
        verify(repository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "name"));
    }


    @Test
    @DisplayName("Deverá retornar um registro salvo no banco")
    void fetchUsers_ReturnOneRegister() {
        final var name = "Fulano";
        final var user = new User(UUID.randomUUID().toString(), name, "fulano@gmail.com", "abcABC");

        when(repository.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(Collections.singletonList(user));

        final var result = service.fetchUsers();

        assertThat(result).isNotNull().isNotEmpty().hasSize(1);
        assertThat(result.get(0).name()).isEqualTo(name);

        verify(repository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "name"));
    }


    @Test
    @DisplayName("Deverá retornar três registros salvos no banco")
    void fetchUsers_ReturnThreeRegister() {
        final var name = "Fulano";
        final var user1 = new User(UUID.randomUUID().toString(), name, "fulano@gmail.com", "abcABC");
        final var user2 = new User(UUID.randomUUID().toString(), "Ciclano", "ciclano@gmail.com", "abcABC");
        final var user3 = new User(UUID.randomUUID().toString(), "Beltrano", "beltrano@gmail.com", "abcABC");

        when(repository.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(List.of(user1, user2, user3));

        final var result = service.fetchUsers();

        assertThat(result).isNotNull().isNotEmpty().hasSize(3);
        assertThat(result.get(0).name()).isEqualTo(name);
        verify(repository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "name"));
    }


    @Test
    @DisplayName("Deverá ocorrer um erro quando o uuid do usuário for nulo ou vazio")
    void getUser_IdNotValid() {
        final var errMessage = "O id do usuário não pode ser nulo ou vázio";

        final var result = catchException(() -> service.getUser(null));
        assertThat(result).isInstanceOf(BadRequestException.class).hasMessage(errMessage);
        verify(repository, never()).findById(anyString());

        final var resultEmpty = catchException(() -> service.getUser(""));
        assertThat(resultEmpty).isInstanceOf(BadRequestException.class).hasMessage(errMessage);
        verify(repository, never()).findById(anyString());

        final var resultEmptyTrim = catchException(() -> service.getUser(" "));
        assertThat(resultEmptyTrim).isInstanceOf(BadRequestException.class).hasMessage(errMessage);
        verify(repository, never()).findById(anyString());
    }

    @Test
    @DisplayName("Deverá ocorrer um erro quando não encontrar o usuário pelo id informado")
    void getUser_UserNotFound() {
        final var uuid = UUID.randomUUID().toString();
        when(repository.findById(any())).thenReturn(Optional.empty());

        final var result = catchException(() -> service.getUser(uuid));
        assertThat(result).isInstanceOf(NotFoundException.class).hasMessage("O usuário com este id não foi encontrado");

        verify(repository, times(1)).findById(anyString());

    }

    @Test
    @DisplayName("Deverá encontrar e retornar o usuário pelo id com sucesso")
    void getUser_UserFounded() {
        final var uuid = UUID.randomUUID().toString();
        final var name = "Fulano da Silva";
        final var email = "fulano@gmail.com";
        final var password = "abcABC123";
        final var request = new UserRegisterRequest(name, email, password, password);
        final var user = User.create(request);
        when(repository.findById(any())).thenReturn(Optional.of(user));

        final var result = service.getUser(uuid);
        assertThat(result).isNotNull();
        assertThat(result.uuid()).isNotNull().isNotEmpty();
        assertThat(result.name()).isNotNull().isNotEmpty().isEqualTo(name);
        assertThat(result.email()).isNotNull().isNotEmpty().isEqualTo(email);

        verify(repository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Deverá ocorrer um erro quando o uuid do usuário for nulo ou vazio quando for deletar")
    void deleteUser_IdNotValid() {
        final var errMessage = "O id do usuário não pode ser nulo ou vázio";

        final var result = catchException(() -> service.deleteUser(null));
        assertThat(result).isInstanceOf(BadRequestException.class).hasMessage(errMessage);
        verify(repository, never()).findById(anyString());
        verify(repository, never()).delete(any());

        final var resultEmpty = catchException(() -> service.deleteUser(""));
        assertThat(resultEmpty).isInstanceOf(BadRequestException.class).hasMessage(errMessage);
        verify(repository, never()).findById(anyString());
        verify(repository, never()).delete(any());

        final var resultEmptyTrim = catchException(() -> service.deleteUser(" "));
        assertThat(resultEmptyTrim).isInstanceOf(BadRequestException.class).hasMessage(errMessage);
        verify(repository, never()).findById(anyString());
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("Deverá ocorrer um erro quando não encontrar o usuário pelo id informado para deletar")
    void deleteUser_UserNotFound() {
        final var uuid = UUID.randomUUID().toString();
        when(repository.findById(any())).thenReturn(Optional.empty());

        final var result = catchException(() -> service.getUser(uuid));
        assertThat(result).isInstanceOf(NotFoundException.class).hasMessage("O usuário com este id não foi encontrado");

        verify(repository, times(1)).findById(anyString());

    }

    @Test
    @DisplayName("Deverá encontrar e retornar o usuário pelo id com sucesso")
    void deleteUser_UserFounded() {
        final var uuid = UUID.randomUUID().toString();
        final var name = "Fulano da Silva";
        final var email = "fulano@gmail.com";
        final var password = "abcABC123";
        final var request = new UserRegisterRequest(name, email, password, password);
        final var user = User.create(request);
        when(repository.findById(any())).thenReturn(Optional.of(user));

        service.deleteUser(uuid);

        verify(repository, times(1)).findById(anyString());
        verify(repository, times(1)).delete(any());
    }
}
