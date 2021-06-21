package com.caionastu.currencyconversion.common.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class BusinessException extends RuntimeException {

    private final String keyMessage;
    private List<Object> arguments = new ArrayList<>();

    public BusinessException(String keyMessage) {
        this.keyMessage = keyMessage;
    }

    public BusinessException(String keyMessage, Object... arguments) {
        this.keyMessage = keyMessage;
        this.arguments = Arrays.asList(arguments.clone());
    }

    public Object[] getArguments() {
        return arguments.toArray();
    }
}
