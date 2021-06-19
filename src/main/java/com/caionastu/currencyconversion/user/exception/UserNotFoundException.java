package com.caionastu.currencyconversion.user.exception;

import com.caionastu.currencyconversion.common.exception.NotFoundException;
import lombok.Getter;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException {

    @Getter
    private final UUID id;

    public UserNotFoundException(UUID id){
        super("user.exception.notFound", id);
        this.id = id;
    }
}
