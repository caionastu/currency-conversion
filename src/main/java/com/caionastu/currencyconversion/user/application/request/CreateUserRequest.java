package com.caionastu.currencyconversion.user.application.request;

import com.caionastu.currencyconversion.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserRequest {

    @NotBlank(message = "{user.name.notBlank}")
    @Size(min = 4, max = 50, message = "{user.name.size}")
    private String name;

}
