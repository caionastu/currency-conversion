package com.caionastu.currencyconversion.conversion.application;

import com.caionastu.currencyconversion.common.application.annotation.ApiPageable;
import com.caionastu.currencyconversion.common.application.response.ApiCollectionResponse;
import com.caionastu.currencyconversion.conversion.application.request.ConversionRequest;
import com.caionastu.currencyconversion.conversion.application.response.ConversionResponse;
import com.caionastu.currencyconversion.conversion.domain.Conversion;
import com.caionastu.currencyconversion.conversion.repository.ConversionRepository;
import com.caionastu.currencyconversion.exchange.service.TaxRateService;
import com.caionastu.currencyconversion.user.domain.User;
import com.caionastu.currencyconversion.user.exception.UserNotFoundException;
import com.caionastu.currencyconversion.user.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/conversions")
@AllArgsConstructor
@Slf4j
@Api(tags = "Conversion Operations")
public class ConversionController {

    private final UserRepository userRepository;
    private final ConversionRepository repository;
    private final TaxRateService taxRateService;

    @ApiPageable
    @GetMapping(path = "/user/{userId}")
    @ApiOperation("Retrieve all conversion transactions from user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful Operation"),
            @ApiResponse(code = 404, message = "User not found")
    })
    public ResponseEntity<ApiCollectionResponse<ConversionResponse>> findByUser(@PathVariable UUID userId, @ApiIgnore Pageable pageable) {
        log.info("Receiving request to find all conversion transactions from user: {}.", userId);

        if (!userRepository.existsById(userId)) {
            log.error("User not found with id: {}", userId);
            throw new UserNotFoundException(userId);
        }

        if (!pageable.getSort().isSorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "date"));
        }

        Page<ConversionResponse> conversions = repository.findByUserId(userId, pageable)
                .map(ConversionResponse::from);

        ApiCollectionResponse<ConversionResponse> response = ApiCollectionResponse.from(conversions);

        log.info("Retrieving all conversion transactions from user: {}.", userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiOperation("Make a conversion between two different currencies")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful Operation"),
            @ApiResponse(code = 400, message = "Invalid Request"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Fail to communicate with Exchange Api or trying to make a conversion with same currency.")
    })
    public ResponseEntity<ConversionResponse> convert(@RequestBody @Valid ConversionRequest request) {
        log.info("Receiving request to convert two currencies. Request: {}.", request);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    log.error("User not find with id: {}", request.getUserId());
                    throw new UserNotFoundException(request.getUserId());
                });

        BigDecimal taxRate = taxRateService.get(request);

        log.info("Saving conversion transaction.");

        Conversion conversion = Conversion.from(request, taxRate, user);
        repository.save(conversion);

        log.info("Conversion transaction saved in database. Id: {}.", conversion.getId());

        ConversionResponse response = ConversionResponse.from(conversion);

        log.info("Conversion request completed.");
        return ResponseEntity.ok(response);
    }

}
