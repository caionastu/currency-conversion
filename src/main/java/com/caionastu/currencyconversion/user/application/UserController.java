package com.caionastu.currencyconversion.user.application;

import com.caionastu.currencyconversion.common.application.annotation.ApiPageable;
import com.caionastu.currencyconversion.common.application.response.ApiCollectionResponse;
import com.caionastu.currencyconversion.user.application.request.CreateUserRequest;
import com.caionastu.currencyconversion.user.application.response.UserResponse;
import com.caionastu.currencyconversion.user.domain.User;
import com.caionastu.currencyconversion.user.exception.UserAlreadyExistException;
import com.caionastu.currencyconversion.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository repository;
    
    @ApiPageable
    @GetMapping
    public ResponseEntity<ApiCollectionResponse<UserResponse>> findAll(@ApiIgnore Pageable pageable) {
        Page<UserResponse> users = repository.findAll(pageable)
                .map(UserResponse::from);

        ApiCollectionResponse<UserResponse> response = ApiCollectionResponse.from(users);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest request) {
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
