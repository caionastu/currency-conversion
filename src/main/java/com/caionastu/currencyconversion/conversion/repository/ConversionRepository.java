package com.caionastu.currencyconversion.conversion.repository;

import com.caionastu.currencyconversion.conversion.domain.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, UUID> {

    Page<Conversion> findByUserId(UUID userId, Pageable pageable);
}
