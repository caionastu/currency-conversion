package com.caionastu.currencyconversion.user.repository;

import com.caionastu.currencyconversion.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByName(String name);
}
