package com.caionastu.currencyconversion.user;

import com.caionastu.currencyconversion.user.application.request.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(value = "classpath:script/initial-insert.sql")
class UserIT {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @ValueSource(strings = {"Charizard", "Azshara", "John", "Kratos", "Gul'Dan", "Yoda"})
    @DisplayName("It should create new user if name is not being used.")
    void createNewUser(String name) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateUserRequest request = new CreateUserRequest(name);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", equalTo(name)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Caio", "Matheus", "José", "Luis", "Luiz"})
    @DisplayName("It should thrown UserAlreadyExistException it creating new user with name that is being used.")
    void errorCreatingUserWithNameThatAlreadyExists(String name) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateUserRequest request = new CreateUserRequest(name);
        String expectedErrorMessage = "User already exist with name " + name + ".";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.details[0]", equalTo(expectedErrorMessage)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "Ar", "Ari", "1234567891234567891234567891234567891234597891234567897987654321657987456123"})
    @DisplayName("It should return BadRequest if name is less than 4 and higher than 50 characters.")
    void nameLength(String name) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateUserRequest request = new CreateUserRequest(name);
        String expectedErrorMessage = "User name must have between 4 and 50 characters.";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details[0]", containsString(expectedErrorMessage)));
        ;
    }

}
