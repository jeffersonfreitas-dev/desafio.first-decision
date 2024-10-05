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
@CrossOrigin("*")
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> fetchUsers(){
        return service.fetchUsers();
    }

    @GetMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@PathVariable String uuid){
        return service.getUser(uuid);
    }

    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String uuid){
        service.deleteUser(uuid);
    }

}
