package com.caionastu.currencyconversion.conversion.application;

import com.caionastu.currencyconversion.conversion.application.request.ConversionRequest;
import com.caionastu.currencyconversion.conversion.application.response.ConversionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/conversions")
public class ConversionController {

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<Void> findByUser(@PathVariable UUID userId, Pageable pageable) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<ConversionResponse> convert(@RequestBody @Valid ConversionRequest request) {

        return ResponseEntity.ok().build();
    }
}
