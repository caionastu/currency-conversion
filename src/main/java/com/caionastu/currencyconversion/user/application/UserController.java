package com.caionastu.currencyconversion.user.application;

import com.caionastu.currencyconversion.user.application.request.CreateUserRequest;
import com.caionastu.currencyconversion.user.application.response.UserResponse;
import com.caionastu.currencyconversion.user.domain.User;
import com.caionastu.currencyconversion.user.exception.UserAlreadyExistException;
import com.caionastu.currencyconversion.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository repository;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody CreateUserRequest request) {
        repository.findByName(request.getName())
                .ifPresent(user -> {
                    throw new UserAlreadyExistException(request.getName());
                });

        User newUser = request.toEntity();
        repository.save(newUser);

        UserResponse response = UserResponse.from(newUser);
        return ResponseEntity.ok(response);
    }
}
