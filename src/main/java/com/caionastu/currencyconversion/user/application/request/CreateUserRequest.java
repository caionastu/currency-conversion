package com.caionastu.currencyconversion.user.application.request;

import com.caionastu.currencyconversion.user.domain.User;
import lombok.Getter;

@Getter
public class CreateUserRequest {
    private String name;

    public User toEntity() {
        return new User(null, name);
    }
}
