package com.example.adapter.in.graphql;

import com.example.core.dto.CustomerProjectionDto;
import com.example.core.port.in.CustomerReadModelQueryPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.graphql.test.autoconfigure.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@GraphQlTest(GraphQLResolver.class)
class GraphQLResolverTest {

    @Autowired
    private GraphQlTester graphQlTester;
    @MockitoBean
    private CustomerReadModelQueryPort queryPort;

    private final static UUID ID = UUID.randomUUID();
    private final static String EMAIL = "test@test.com";
    private final static String PHONE_NUMBER = "+78002002316";
    private final static String USER_NAME = "test";
    private final static String FIRST_NAME = "Test";
    private final static String LAST_NAME = "Test";
    private final static String COUNTRY = "Россия";
    private final static String ZIPCODE = "103132";
    private final static String CITY = "Москва";
    private final static String STREET = "Красная площадь";
    private final static String BUILDING = "7";
    private final static String APARTMENT = "1";
    private final static String STATUS = "ACTIVE";
    private final static int LOYALTY_POINTS = 10;

    private final static String GRAPHQL_QUERY = """
            query {
                customer(id: "%s") {
                    id
                    email
                    userName
                    phone
                    firstName
                    lastName
                    country
                    zipcode
                    city
                    street
                    building
                    apartment
                    status
                    loyaltyPoints
                    deleted
                }
            }
            """.formatted(ID);

    @Test
    void shouldReturnDto_whenCustomerIsPresent() {
        var expectedDto = new CustomerProjectionDto(
                ID,
                EMAIL,
                PHONE_NUMBER,
                USER_NAME,
                FIRST_NAME,
                LAST_NAME,
                COUNTRY,
                ZIPCODE,
                CITY,
                STREET,
                BUILDING,
                APARTMENT,
                STATUS,
                LOYALTY_POINTS,
                false
        );
        when(queryPort.findById(ID)).thenReturn(Optional.of(expectedDto));

        graphQlTester.document(GRAPHQL_QUERY)
                .execute()
                .path("data.customer")
                .entity(CustomerProjectionDto.class)
                .isEqualTo(expectedDto);
        verify(queryPort).findById(ID);
    }
}
