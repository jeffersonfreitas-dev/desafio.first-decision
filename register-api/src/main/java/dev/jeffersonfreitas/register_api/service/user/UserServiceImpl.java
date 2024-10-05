package dev.jeffersonfreitas.register_api.service.user;

import dev.jeffersonfreitas.register_api.dto.user.UserRegisterRequest;
import dev.jeffersonfreitas.register_api.dto.user.UserResponse;
import dev.jeffersonfreitas.register_api.exceptions.AlreadyRegisteredException;
import dev.jeffersonfreitas.register_api.exceptions.BadRequestException;
import dev.jeffersonfreitas.register_api.exceptions.BusinessException;
import dev.jeffersonfreitas.register_api.exceptions.NotFoundException;
import dev.jeffersonfreitas.register_api.model.User;
import dev.jeffersonfreitas.register_api.repository.UserRepository;
import jakarta.persistence.PersistenceException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class.getName());

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserResponse create(final UserRegisterRequest request) {
        if(request == null) throw new BadRequestException("O objeto de requisição não pode ser nulo");

        request.passwordConfirmationValidate();
        userAlreadyRegistered(request);
        User user = User.create(request);

        try {
            repository.save(user);
            return UserResponse.from(user);
        } catch (DataAccessException | TransactionSystemException | PersistenceException e) {
            LOG.log(Level.SEVERE, String.format("Erro ao tentar salvar o usuário no banco: %s", e.getMessage()));
            throw new BusinessException("Ocorreu um erro ao tentar salvar o usuário no banco. Favor, tente novamente.");
        }
    }

    @Override
    public List<UserResponse> fetchUsers() {
        //TODO: Podemos pensar em futuramente usar paginação a medida que a base de usuários aumente.
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream().map(UserResponse::from).toList();
    }

    @Override
    public UserResponse getUser(final String uuid) {
        User user = this.findById(uuid);
        return UserResponse.from(user);
    }

    @Override
    @Transactional
    public void deleteUser(String uuid) {
        User user = this.findById(uuid);

        try {
            repository.delete(user);
        }catch (DataIntegrityViolationException | TransactionSystemException e){
            LOG.log(Level.SEVERE, String.format("Erro ao tentar excluir o usuário no banco: %s", e.getMessage()));
            throw new BusinessException("Não foi possível deletar o usuário.");
        }
    }

    private User findById(final String uuid) {
        if(uuid == null || uuid.isBlank()) throw new BadRequestException("O id do usuário não pode ser nulo ou vázio");
        return repository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("O usuário com este id não foi encontrado"));
    }

    private void userAlreadyRegistered(UserRegisterRequest request) {
        Optional<User> optUser = repository.findByEmailIgnoreCase(request.email());
        if(optUser.isPresent()) throw new AlreadyRegisteredException("Já existe um usuário com este e-mail cadastrado");
    }
}
