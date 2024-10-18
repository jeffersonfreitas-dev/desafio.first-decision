package dev.jeffersonfreitas.register_api.utils.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StrongPasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StrongPassword {
    String message() default "A senha deve conter letras maiúsculas, minúsculas, números e caracteres especiais";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
