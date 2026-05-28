package com.example.adapter.in.rest;

import com.example.adapter.in.rest.dto.RegisterCustomerDto;
import com.example.adapter.in.rest.mapper.CustomerWebMapperImpl;
import com.example.application.command.RegisterCustomerCommand;
import com.example.core.domain.valueobject.Address;
import com.example.core.domain.valueobject.Email;
import com.example.core.domain.valueobject.FullName;
import com.example.core.domain.valueobject.PhoneNumber;
import com.example.core.domain.valueobject.UserName;
import com.example.core.port.in.CustomerCommandPort;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerRestController.class)
@Import(CustomerWebMapperImpl.class)
class CustomerRestControllerTest {

    private static final String VALID_REGISTER_REQUEST = """
            {
                "email": "test@test.com",
                "phone": "+78002002316",
                "userName": "test",
                "firstName": "Test",
                "lastName": "Test",
                "country": "Россия",
                "zipcode": "103132",
                "city": "Москва",
                "street": "Красная площадь",
                "building": "7",
                "apartment": "1"
            }
            """;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper jsonMapper;
    @MockitoBean
    CustomerCommandPort commandPort;

    @Nested
    class RegistrationTest {

        @Test
        void shouldRegister_whenRequestValid() throws Exception {
            var expectedDto = jsonMapper.readValue(VALID_REGISTER_REQUEST, RegisterCustomerDto.class);

            mockMvc.perform(post("/api/v1/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(VALID_REGISTER_REQUEST))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.*", hasSize(2)))
                    .andExpect(jsonPath("$.statusCode").value(201))
                    .andExpect(jsonPath("$.message").value("Customer with email test@test.com created"));

            var commandCaptor = ArgumentCaptor.forClass(RegisterCustomerCommand.class);
            verify(commandPort).register(commandCaptor.capture());
            var command = commandCaptor.getValue();
            assertThat(command)
                    .extracting(
                            RegisterCustomerCommand::email,
                            RegisterCustomerCommand::phone,
                            RegisterCustomerCommand::userName,
                            RegisterCustomerCommand::fullName,
                            RegisterCustomerCommand::address
                    )
                    .containsExactly(
                            Email.of(expectedDto.email()),
                            PhoneNumber.of(expectedDto.phone()),
                            UserName.of(expectedDto.userName()),
                            FullName.of(expectedDto.firstName(), expectedDto.lastName()),
                            Address.builder()
                                    .country(expectedDto.country())
                                    .zipcode(expectedDto.zipcode())
                                    .city(expectedDto.city())
                                    .street(expectedDto.street())
                                    .building(expectedDto.building())
                                    .apartment(expectedDto.apartment())
                                    .build()
                    );
        }
    }
}
