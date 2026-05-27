package com.example.adapter.out.persistence.read;

import com.example.core.domain.enumeration.CustomerStatus;
import com.example.core.dto.AddLoyaltyPointsReadModelDto;
import com.example.core.dto.BlockCustomerReadModelDto;
import com.example.core.dto.CreateCustomerReadModelDto;
import com.example.core.dto.MarkCustomerDeletedReadModelDto;
import com.example.core.dto.SubtractLoyaltyPointsReadModelDto;
import com.example.core.dto.UnblockCustomerReadModelDto;
import com.example.core.dto.UpdateCustomerAddressReadModelDto;
import com.example.core.dto.UpdateCustomerEmailReadModelDto;
import com.example.core.dto.UpdateCustomerFullNameReadModelDto;
import com.example.core.dto.UpdateCustomerPhoneReadModelDto;
import com.example.core.dto.UpdateCustomerUserNameReadModelDto;
import com.example.core.port.out.CustomerReadModelCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcCustomerReadModelCommandAdapter implements CustomerReadModelCommandPort {

    private static final String INSERT_QUERY = """
            INSERT INTO customer_projection
            (id, email, phone, user_name, first_name, last_name,
            country, zipcode, city, street, building, apartment,
            status, loyalty_points, deleted)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'ACTIVE', 0, false)
            ON CONFLICT (id) DO NOTHING
            """;
    private static final String UPDATE_EMAIL_QUERY = """
            UPDATE customer_projection
            SET email = ?
            WHERE id = ?
            """;
    private static final String UPDATE_PHONE_QUERY = """
            UPDATE customer_projection
            SET phone = ?
            WHERE id = ?
            """;
    private static final String UPDATE_USERNAME_QUERY = """
            UPDATE customer_projection
            SET user_name = ?
            WHERE id = ?
            """;
    private static final String UPDATE_FULL_NAME_QUERY = """
            UPDATE customer_projection
            SET first_name = ?, last_name = ?
            WHERE id = ?
            """;
    private static final String UPDATE_ADDRESS_QUERY = """
            UPDATE customer_projection
            SET country = ?, zipcode = ?,
            city = ?, street = ?,
            building = ?, apartment = ?
            WHERE id = ?
            """;
    private static final String UPDATE_STATUS_QUERY = """
            UPDATE customer_projection
            SET status = ?
            WHERE id = ?
            """;
    private static final String SOFT_DELETE_QUERY = """
            UPDATE customer_projection
            SET deleted = TRUE
            WHERE id = ?
            """;
    private static final String ADD_LOYALTY_POINTS_QUERY = """
            UPDATE customer_projection
            SET loyalty_points = loyalty_points + ?
            WHERE id = ?
            """;
    private static final String SUBTRACT_LOYALTY_POINTS_QUERY = """
            UPDATE customer_projection
            SET loyalty_points = loyalty_points - ?
            WHERE id = ?
            """;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void createCustomer(CreateCustomerReadModelDto dto) {
        jdbcTemplate.update(
                INSERT_QUERY,
                dto.customerId(), dto.email(), dto.phone(),
                dto.userName(), dto.firstName(), dto.lastName(),
                dto.country(), dto.zipcode(), dto.city(),
                dto.street(), dto.building(), dto.apartment()
        );
    }

    @Override
    public void updateEmail(UpdateCustomerEmailReadModelDto dto) {
        jdbcTemplate.update(UPDATE_EMAIL_QUERY, dto.newEmail(), dto.customerId());
    }

    @Override
    public void updatePhone(UpdateCustomerPhoneReadModelDto dto) {
        jdbcTemplate.update(UPDATE_PHONE_QUERY, dto.newPhone(), dto.customerId());
    }

    @Override
    public void updateUserName(UpdateCustomerUserNameReadModelDto dto) {
        jdbcTemplate.update(UPDATE_USERNAME_QUERY, dto.newName(), dto.id());
    }

    @Override
    public void updateFullName(UpdateCustomerFullNameReadModelDto dto) {
        jdbcTemplate.update(UPDATE_FULL_NAME_QUERY, dto.firstName(), dto.lastName(), dto.id());
    }

    @Override
    public void updateAddress(UpdateCustomerAddressReadModelDto dto) {
        jdbcTemplate.update(
                UPDATE_ADDRESS_QUERY,
                dto.country(), dto.zipcode(),
                dto.city(), dto.street(),
                dto.building(), dto.apartment(),
                dto.customerId()
        );
    }

    @Override
    public void blockCustomer(BlockCustomerReadModelDto dto) {
        jdbcTemplate.update(UPDATE_STATUS_QUERY, CustomerStatus.BLOCKED, dto.id());
    }

    @Override
    public void unblockCustomer(UnblockCustomerReadModelDto dto) {
        jdbcTemplate.update(UPDATE_STATUS_QUERY, CustomerStatus.ACTIVE, dto.id());
    }

    @Override
    public void markDeleted(MarkCustomerDeletedReadModelDto dto) {
        jdbcTemplate.update(SOFT_DELETE_QUERY, dto.id());
    }

    @Override
    public void addLoyaltyPoints(AddLoyaltyPointsReadModelDto dto) {
        jdbcTemplate.update(ADD_LOYALTY_POINTS_QUERY, dto.points(), dto.id());
    }

    @Override
    public void subtractLoyaltyPoints(SubtractLoyaltyPointsReadModelDto dto) {
        jdbcTemplate.update(SUBTRACT_LOYALTY_POINTS_QUERY, dto.points(), dto.id());
    }
}
