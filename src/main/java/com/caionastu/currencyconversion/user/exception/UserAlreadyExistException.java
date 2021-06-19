package com.caionastu.currencyconversion.user.exception;

import com.caionastu.currencyconversion.common.exception.BusinessException;
import lombok.Getter;

public class UserAlreadyExistException extends BusinessException {

    @Getter
    private final String name;

    public UserAlreadyExistException(String name) {
        super("user.exception.exist", name);
        this.name = name;
    }
}
