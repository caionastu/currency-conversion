package com.caionastu.currencyconversion.user.exception;

import com.caionastu.currencyconversion.common.exception.NotFoundException;

public class UserAlreadyExistException extends NotFoundException {

    private String name;

    public UserAlreadyExistException(String name) {
        super("{user.exist}", name);
    }
}
