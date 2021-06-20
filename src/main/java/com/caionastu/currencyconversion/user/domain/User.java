package com.caionastu.currencyconversion.user.domain;

import com.caionastu.currencyconversion.user.application.request.CreateUserRequest;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String name;

    public static User from(@NonNull CreateUserRequest request) {
        return new User(null, request.getName());
    }

}
