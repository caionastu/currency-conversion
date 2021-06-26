package com.caionastu.currencyconversion.user.application;

import com.caionastu.currencyconversion.common.application.annotation.ApiPageable;
import com.caionastu.currencyconversion.common.application.response.ApiCollectionResponse;
import com.caionastu.currencyconversion.user.application.request.CreateUserRequest;
import com.caionastu.currencyconversion.user.application.response.UserResponse;
import com.caionastu.currencyconversion.user.domain.User;
import com.caionastu.currencyconversion.user.exception.UserAlreadyExistException;
import com.caionastu.currencyconversion.user.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags = "User Operations")
public class UserController {

    private final UserRepository repository;
    
    @GetMapping
    @ApiPageable
    @ApiOperation("Retrieve all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful Operation")
    })
    public ResponseEntity<ApiCollectionResponse<UserResponse>> findAll(@ApiIgnore Pageable pageable) {
        log.info("Receiving request to find all users.");

        Page<UserResponse> users = repository.findAll(pageable)
                .map(UserResponse::from);

        ApiCollectionResponse<UserResponse> response = ApiCollectionResponse.from(users);

        log.info("Retrieving all users.");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiOperation("Create new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful Operation"),
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 500, message = "Name already in use")
    })
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest request) {
        log.info("Receiving request to create new user with name: {}.", request.getName());

        if (repository.existsByName(request.getName())) {
            log.error("Error creating user. Name {} is already in use.", request.getName());
            throw new UserAlreadyExistException(request.getName());
        }

        User newUser = User.from(request);
        repository.save(newUser);

        log.info("User created. Id: {}.", newUser.getId());

        UserResponse response = UserResponse.from(newUser);
        return ResponseEntity.ok(response);
    }

}
