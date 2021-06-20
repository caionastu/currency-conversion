package com.caionastu.currencyconversion.conversion;

import com.caionastu.currencyconversion.conversion.application.request.ConversionRequest;
import com.caionastu.currencyconversion.exchange.infrastructure.ExchangeClient;
import com.caionastu.currencyconversion.exchange.infrastructure.response.ExchangeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "classpath:script/initial-insert.sql")
@Transactional
class ConversionIT {

    private static final UUID validUserId = UUID.fromString("112bd468-1853-45b7-8d91-bda107dfcf7e");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeClient client;

    @Test
    @DisplayName("It should return conversion transactions ordered by descending date (default).")
    void findByUser() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/conversions/user/{userID}", validUserId)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasNext", equalTo(false)))
                .andExpect(jsonPath("$.items[0].id", equalTo("19eecdbd-553d-45c8-9b18-ecc60d187d43")))
                .andExpect(jsonPath("$.items[0].userId", equalTo(validUserId.toString())))
                .andExpect(jsonPath("$.items[0].originCurrency", equalTo("BRL")))
                .andExpect(jsonPath("$.items[0].originValue", equalTo(10)))
                .andExpect(jsonPath("$.items[0].destinyCurrency", equalTo("EUR")))
                .andExpect(jsonPath("$.items[0].destinyValue", equalTo(0.15487)))
                .andExpect(jsonPath("$.items[0].taxRate", equalTo(0.015487)))
                .andExpect(jsonPath("$.items[0].date", equalTo("2021-06-21T10:04:00Z")))
                .andExpect(jsonPath("$.items[1].id", equalTo("cc48f2d0-8094-41cb-a6d4-4872a191e118")))
                .andExpect(jsonPath("$.items[2].id", equalTo("f739ec70-58c1-4439-8e9b-dac872ddc28d")))
                .andExpect(jsonPath("$.items[3].id", equalTo("4a49bf66-94ae-492d-a608-063cce2245e8")))
                .andExpect(jsonPath("$.items[4].id", equalTo("543a6c48-dec2-489b-902d-49a25524b45e")))
                .andReturn();

    }

    @Test
    @DisplayName("It should return conversion transactions with custom pagination.")
    void findByUserWithCustomPagination() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/conversions/user/{userID}", validUserId)
                .param("page", "0")
                .param("size", "2")
                .param("sort", "date,asc")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasNext", equalTo(true)))
                .andExpect(jsonPath("$.pageSize", equalTo(2)))
                .andExpect(jsonPath("$.pageNumber", equalTo(0)))
                .andExpect(jsonPath("$.totalElements", equalTo(5)))
                .andExpect(jsonPath("$.items[0].id", equalTo("543a6c48-dec2-489b-902d-49a25524b45e")))
                .andExpect(jsonPath("$.items[0].userId", equalTo(validUserId.toString())))
                .andExpect(jsonPath("$.items[0].originCurrency", equalTo("BRL")))
                .andExpect(jsonPath("$.items[0].originValue", equalTo(10)))
                .andExpect(jsonPath("$.items[0].destinyCurrency", equalTo("USD")))
                .andExpect(jsonPath("$.items[0].destinyValue", equalTo(19.6487)))
                .andExpect(jsonPath("$.items[0].taxRate", equalTo(1.96487)))
                .andExpect(jsonPath("$.items[0].date", equalTo("2021-06-21T10:00:00Z")))
                .andExpect(jsonPath("$.items[1].id", equalTo("4a49bf66-94ae-492d-a608-063cce2245e8")))
                .andReturn();

    }

    @Test
    @DisplayName("It should throw UserNotFoundException if user doesn't exist.")
    void findByUserThrowUserNotFound() throws Exception {
        UUID invalidId = UUID.randomUUID();
        String expectedErrorMessage = "User not found with id " + invalidId + ".";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/conversions/user/{userID}", invalidId)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.details[0]", equalTo(expectedErrorMessage)));

    }

    @Test
    @DisplayName("It should convert between two currencies.")
    void convert() throws Exception {
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("BRL", BigDecimal.valueOf(1.23548));
        rates.put("USD", BigDecimal.valueOf(0.321548));

        ExchangeResponse response = new ExchangeResponse(rates);
        ResponseEntity<ExchangeResponse> responseEntity = ResponseEntity.ok(response);
        when(client.getTaxRate(any())).thenReturn(responseEntity);

        ObjectMapper objectMapper = new ObjectMapper();
        ConversionRequest request = new ConversionRequest(
                validUserId,
                "BRL",
                "USD",
                BigDecimal.TEN
        );

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/conversions")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId", equalTo(validUserId.toString())))
                .andExpect(jsonPath("$.destinyValue").exists())
                .andExpect(jsonPath("$.taxRate").exists());
    }

    @Test
    @DisplayName("It should throw UserNotFoundException if user doesn't exist.")
    void convertThrowUserNotFound() throws Exception {
        UUID invalidId = UUID.randomUUID();
        String expectedErrorMessage = "User not found with id " + invalidId + ".";
        ObjectMapper objectMapper = new ObjectMapper();
        ConversionRequest request = new ConversionRequest(
                invalidId,
                "BRL",
                "USD",
                BigDecimal.TEN
        );

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/conversions")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.details[0]", equalTo(expectedErrorMessage)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"B", "bb", "POIR", "AbCDE"})
    @DisplayName("It should return BadRequest if origin currency is bad formatted.")
    void originCurrencyBadRequest(String originCurrency) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ConversionRequest request = new ConversionRequest(
                validUserId,
                originCurrency,
                "BRL",
                BigDecimal.TEN
        );

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/conversions")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"B", "bb", "POIR", "AbCDE"})
    @DisplayName("It should return BadRequest if destiny currency is bad formatted.")
    void destinyCurrencyBadRequest(String destinyCurrency) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ConversionRequest request = new ConversionRequest(
                validUserId,
                "BRL",
                destinyCurrency,
                BigDecimal.TEN
        );

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/conversions")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

}
