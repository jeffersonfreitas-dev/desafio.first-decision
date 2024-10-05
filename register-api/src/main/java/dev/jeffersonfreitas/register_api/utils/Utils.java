package dev.jeffersonfreitas.register_api.utils;

import dev.jeffersonfreitas.register_api.exceptions.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Utils {

    private Utils() {}

    public static String encryptPassword(final String password) {
        if(password == null || password.trim().isBlank())
            throw new BadRequestException("A senha não pode ser nula ou vazia");
        return new BCryptPasswordEncoder().encode(password);
    }
}
