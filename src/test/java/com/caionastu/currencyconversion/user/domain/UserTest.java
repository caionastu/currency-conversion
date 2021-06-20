package com.caionastu.currencyconversion.user.domain;

import com.caionastu.currencyconversion.user.application.request.CreateUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unit")
public class UserTest {

    @Test
    @DisplayName("It should create user from CreateUserRequest.")
    void createUser() {
        CreateUserRequest request = new CreateUserRequest("User Name");
        User user = User.from(request);

        assertThat(user.getId()).isNull();
        assertThat(user.getName()).isEqualTo(request.getName());
    }

    @Test
    @DisplayName("It should throw NullPointerException if CreateUserRequest is null.")
    void failToCreateUser() {
        assertThatThrownBy(() -> {
            User.from(null);
        }).isInstanceOf(NullPointerException.class);
    }
}
