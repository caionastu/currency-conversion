package com.caionastu.currencyconversion.user.application.response;

import com.caionastu.currencyconversion.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserResponse {

    private UUID id;
    private String name;

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName());
    }
}
