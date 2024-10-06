package dev.jeffersonfreitas.register_api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.jeffersonfreitas.register_api.exceptions.BadRequestException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(

        @NotBlank(message = "O name é obrigatório")
        @Size(min = 3, max = 50, message = "O nome deverá conter de 3 a 50 caracteres")
        String name,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Informe um email válido")
        String email,

        @NotBlank(message = "O password é obrigatório")
        @Size(min = 6, max = 20, message = "O password deverá conter de 6 a 20 caracteres")
        String password,

        @NotBlank(message = "O password_confirmation é obrigatório")
        @JsonProperty("password_confirmation")
        String passwordConfirmation) {

    public void passwordConfirmationValidate() {
       if(!this.passwordConfirmation.equals(password))
           throw new BadRequestException("A confirmação de senha deverá ser igual a senha");
    }
}
