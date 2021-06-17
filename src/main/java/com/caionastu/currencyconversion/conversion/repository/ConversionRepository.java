package com.caionastu.currencyconversion.conversion.repository;

import com.caionastu.currencyconversion.conversion.domain.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConversionRepository extends JpaRepository<Conversion, UUID> {
}
