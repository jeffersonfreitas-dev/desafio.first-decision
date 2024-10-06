package dev.jeffersonfreitas.register_api.repository;

import dev.jeffersonfreitas.register_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailIgnoreCase(final String email);
}
