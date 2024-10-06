package dev.jeffersonfreitas.register_api.dto.user;

import dev.jeffersonfreitas.register_api.exceptions.BusinessException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRegisterRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Test
    @DisplayName("Não deverá possuir erros já que foi passado todos os campos corretamente")
    void IsValid() {
        final var name = "Fulano da silva";
        final var email = "fulano@gmail.com";
        final var password = "abcABC123";
        final var passwordConfirmation = "abcABC123";

        final var request = new UserRegisterRequest(name, email, password, passwordConfirmation);
        Set<ConstraintViolation<UserRegisterRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Deverá possuir erros quando o name não for valido")
    void name_shouldFailWhenNameInvalid() {
        final var errMessageMandatory = "O name é obrigatório";
        final var errMessageSize = "O nome deverá conter de 3 a 50 caracteres";

        final var email = "fulano@gmail.com";
        final var password = "abcABC123";
        final var passwordConfirmation = "abcABC123";

        //Quando name for nulo
        final var request = new UserRegisterRequest(null, email, password, passwordConfirmation);
        Set<ConstraintViolation<UserRegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(errMessageMandatory)));

        //Quando o name for vazio
        final var requestEmpty = new UserRegisterRequest("", email, password, passwordConfirmation);
        Set<ConstraintViolation<UserRegisterRequest>> violationsEmpty = validator.validate(requestEmpty);
        assertFalse(violationsEmpty.isEmpty());
        assertTrue(violationsEmpty.stream().anyMatch(violation -> violation.getMessage().equals(errMessageMandatory)));

        //Quando o name tiver menos que 3 caracteres
        final var requestMinus = new UserRegisterRequest("ab", email, password, passwordConfirmation);
        Set<ConstraintViolation<UserRegisterRequest>> violationsMinus = validator.validate(requestMinus);
        assertFalse(violationsMinus.isEmpty());
        assertTrue(violationsMinus.stream().anyMatch(violation -> violation.getMessage().equals(errMessageSize)));

        //Quando o name tiver mais que 50 caracteres
        final var requestMax = new UserRegisterRequest(UUID.randomUUID().toString().concat(UUID.randomUUID().toString()),
                email, password, passwordConfirmation);
        Set<ConstraintViolation<UserRegisterRequest>> violationsMax = validator.validate(requestMax);
        assertFalse(violationsMax.isEmpty());
        assertTrue(violationsMax.stream().anyMatch(violation -> violation.getMessage().equals(errMessageSize)));
    }



    @Test
    @DisplayName("Deverá possuir erros quando o email não for valido")
    void email_shouldFailWhenEmailInvalid() {
        final var errMessageMandatory = "O email é obrigatório";
        final var errMessageValid = "Informe um email válido";

        final var name = "Fulano da Silva";
        final var password = "abcABC123";
        final var passwordConfirmation = "abcABC123";

        //Quando email for nulo
        final var request = new UserRegisterRequest(name, null, password, passwordConfirmation);
        Set<ConstraintViolation<UserRegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(errMessageMandatory)));

        //Quando o email for vazio
        final var requestEmpty = new UserRegisterRequest(name, "", password, passwordConfirmation);
        Set<ConstraintViolation<UserRegisterRequest>> violationsEmpty = validator.validate(requestEmpty);
        assertFalse(violationsEmpty.isEmpty());
        assertTrue(violationsEmpty.stream().anyMatch(violation -> violation.getMessage().equals(errMessageMandatory)));

        //Quando o email tiver o formado inválido
        final var requestInvalid = new UserRegisterRequest(name, "fulano", password, passwordConfirmation);
        Set<ConstraintViolation<UserRegisterRequest>> violationsValid = validator.validate(requestInvalid);
        assertFalse(violationsValid.isEmpty());
        assertTrue(violationsValid.stream().anyMatch(violation -> violation.getMessage().equals(errMessageValid)));
    }


    @Test
    @DisplayName("Deverá possuir erros quando o password não for valido")
    void password_shouldFailWhenPasswordInvalid() {
        final var errMessageMandatory = "O password é obrigatório";
        final var errMessageSize = "O password deverá conter de 6 a 20 caracteres";

        final var name = "Fulano da Silva";
        final var email = "fulano@email.com";
        final var passwordConfirmation = "abcABC123";

        //Quando password for nulo
        final var request = new UserRegisterRequest(name, email, null, passwordConfirmation);
        Set<ConstraintViolation<UserRegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(errMessageMandatory)));

        //Quando o password for vazio
        final var requestEmpty = new UserRegisterRequest(name, email, "", passwordConfirmation);
        Set<ConstraintViolation<UserRegisterRequest>> violationsEmpty = validator.validate(requestEmpty);
        assertFalse(violationsEmpty.isEmpty());
        assertTrue(violationsEmpty.stream()
                .allMatch(violation -> violation.getMessage().equals(errMessageMandatory)
                        || violation.getMessage().equals(errMessageSize)));

        //Quando o password tiver menos que 6 caracteres
        final var requestMinus = new UserRegisterRequest(name, email, "abcAB", passwordConfirmation);
        Set<ConstraintViolation<UserRegisterRequest>> violationsMinus = validator.validate(requestMinus);
        assertFalse(violationsMinus.isEmpty());
        assertTrue(violationsMinus.stream()
                .allMatch(violation -> violation.getMessage().equals(errMessageMandatory)
                        || violation.getMessage().equals(errMessageSize)));
    }


    @Test
    @DisplayName("Deverá possuir erros quando o password_confirmation não for valido")
    void passwordConfirmation_shouldFailWhenPasswordConfirmationInvalid() {
        final var errMessageMandatory = "O password_confirmation é obrigatório";

        final var name = "Fulano da Silva";
        final var email = "fulano@email.com";
        final var password = "abcABC123";

        //Quando confirmation for nulo
        final var request = new UserRegisterRequest(name, email, password, null);
        Set<ConstraintViolation<UserRegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(errMessageMandatory)));

        //Quando o confirmation for vazio
        final var requestEmpty = new UserRegisterRequest(name, email, password, "");
        Set<ConstraintViolation<UserRegisterRequest>> violationsEmpty = validator.validate(requestEmpty);
        assertFalse(violationsEmpty.isEmpty());
        assertTrue(violationsEmpty.stream()
                .anyMatch(violation -> violation.getMessage().equals(errMessageMandatory)));
    }
}
