package dev.jeffersonfreitas.register_api.model;

import dev.jeffersonfreitas.register_api.dto.user.UserRegisterRequest;
import dev.jeffersonfreitas.register_api.utils.Utils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "uuid", length = 40, nullable = false, unique = true)
    private String uuid;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "email", length = 120, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 150, nullable = false)
    private String password;

    public User() {}

    public User(String uuid, String name, String email, String password) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User create(UserRegisterRequest request) {
        String uuid = UUID.randomUUID().toString();
        String encryptedPassword = Utils.encryptPassword(request.password());
        return new User(uuid, request.name(), request.email(), encryptedPassword);
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
