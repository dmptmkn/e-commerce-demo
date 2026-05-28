package com.example.adapter.out.persistence.read;

import com.example.core.dto.CustomerProjectionDto;
import com.example.core.port.out.CustomerReadModelQueryOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JdbcCustomerReadModelQueryAdapter implements CustomerReadModelQueryOutPort {

    private static final String FIND_ALL_QUERY = """
            SELECT id, email, phone,
            user_name, first_name, last_name,
            country, zipcode, city,
            street, building, apartment,
            status, loyalty_points, deleted
            FROM customer_projection
            """;
    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE id = ? LIMIT 1";
    private static final String FIND_BY_EMAIL_QUERY = FIND_ALL_QUERY + " WHERE email = ? LIMIT 1";
    private static final String FIND_BY_USER_NAME_QUERY = FIND_ALL_QUERY + " WHERE user_name = ? LIMIT 1";

    private final RowMapper<CustomerProjectionDto> rowMapper = (rs, rowNum) ->
            new CustomerProjectionDto(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("user_name"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("country"),
                    rs.getString("zipcode"),
                    rs.getString("city"),
                    rs.getString("street"),
                    rs.getString("building"),
                    rs.getString("apartment"),
                    rs.getString("status"),
                    rs.getInt("loyalty_points"),
                    rs.getBoolean("deleted")
            );
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<CustomerProjectionDto> findById(UUID id) {
        return returnOptionalForQuery(FIND_BY_ID_QUERY, id);
    }

    @Override
    public List<CustomerProjectionDto> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, rowMapper);
    }

    @Override
    public Optional<CustomerProjectionDto> findByEmail(String email) {
        return returnOptionalForQuery(FIND_BY_EMAIL_QUERY, email);
    }

    @Override
    public Optional<CustomerProjectionDto> findByUserName(String userName) {
        return returnOptionalForQuery(FIND_BY_USER_NAME_QUERY, userName);
    }

    private Optional<CustomerProjectionDto> returnOptionalForQuery(String query, Object... args) {
        try {
            var result = jdbcTemplate.queryForObject(query, rowMapper, args);
            return Optional.of(result);
        } catch (EmptyResultDataAccessException e) {
            log.debug("No result for query: {}", query);
            return Optional.empty();
        }
    }
}
