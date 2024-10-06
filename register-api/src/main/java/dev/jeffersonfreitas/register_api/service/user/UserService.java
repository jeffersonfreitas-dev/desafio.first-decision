package dev.jeffersonfreitas.register_api.service.user;

import dev.jeffersonfreitas.register_api.dto.user.UserRegisterRequest;
import dev.jeffersonfreitas.register_api.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create(final UserRegisterRequest request);

    List<UserResponse> fetchUsers();

    UserResponse getUser(final String uuid);

    void deleteUser(final String uuid);
}
