package com.caionastu.currencyconversion.user.repository;

import com.caionastu.currencyconversion.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByName(String name);
}
