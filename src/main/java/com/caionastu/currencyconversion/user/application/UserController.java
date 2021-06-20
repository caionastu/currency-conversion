package com.caionastu.currencyconversion.user.application;

import com.caionastu.currencyconversion.common.application.annotation.ApiPageable;
import com.caionastu.currencyconversion.common.application.response.ApiCollectionResponse;
import com.caionastu.currencyconversion.user.application.request.CreateUserRequest;
import com.caionastu.currencyconversion.user.application.response.UserResponse;
import com.caionastu.currencyconversion.user.domain.User;
import com.caionastu.currencyconversion.user.exception.UserAlreadyExistException;
import com.caionastu.currencyconversion.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository repository;
    
    @ApiPageable
    @GetMapping
    public ResponseEntity<ApiCollectionResponse<UserResponse>> findAll(@ApiIgnore Pageable pageable) {
        log.info("Receiving request to find all users.");

        Page<UserResponse> users = repository.findAll(pageable)
                .map(UserResponse::from);

        ApiCollectionResponse<UserResponse> response = ApiCollectionResponse.from(users);

        log.info("Retrieving all users.");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest request) {
        log.info("Receiving request to create new user with name: {}.", request.getName());

        repository.findByName(request.getName())
                .ifPresent(user -> {
                    log.error("Error creating user. Name {} is already in use.", request.getName());
                    throw new UserAlreadyExistException(request.getName());
                });

        User newUser = request.toEntity();
        repository.save(newUser);

        log.info("User created. Id: {}.", newUser.getId());

        UserResponse response = UserResponse.from(newUser);
        return ResponseEntity.ok(response);
    }

}
