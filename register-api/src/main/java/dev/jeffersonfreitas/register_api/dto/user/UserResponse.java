package dev.jeffersonfreitas.register_api.dto.user;

import dev.jeffersonfreitas.register_api.model.User;

public record UserResponse(
        String uuid,
        String name,
        String email) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getUuid(), user.getName(), user.getEmail());
    }
}
