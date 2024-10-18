package dev.jeffersonfreitas.register_api.controller;

import dev.jeffersonfreitas.register_api.dto.user.UserRegisterRequest;
import dev.jeffersonfreitas.register_api.dto.user.UserResponse;
import dev.jeffersonfreitas.register_api.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:4200")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse create(@RequestBody @Valid UserRegisterRequest request) {
        return service.create(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponse> fetchUsers(){
        return service.fetchUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getUser(@PathVariable String uuid){
        return service.getUser(uuid);
    }

    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String uuid){
        service.deleteUser(uuid);
    }

}
