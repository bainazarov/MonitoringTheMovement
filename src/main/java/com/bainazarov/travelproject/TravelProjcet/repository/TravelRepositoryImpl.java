package com.bainazarov.travelproject.TravelProjcet.repository;

import com.bainazarov.travelproject.TravelProjcet.dto.ClientDto;
import com.bainazarov.travelproject.TravelProjcet.entity.Travel;
import com.bainazarov.travelproject.TravelProjcet.entity.TravelAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TravelRepositoryImpl implements TravelRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TravelRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addTravel(Travel travel) {
        String sql = "INSERT INTO travel.store_travel.travels (client_id, name, surname, birthday, transport_type, departure_city_name, " +
                "destination_city_name, departure_time, destination_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, travel.getClient_id(), travel.getName(), travel.getSurname(), travel.getBirthday(), travel.getTransport_type().toString(),
                travel.getDeparture_city_name(), travel.getDestination_city_name(), travel.getDeparture_time(), travel.getDestination_time());
    }

    @Override
    public List<ClientDto> getAllClients() {
        String sql = "SELECT client_id, name, surname FROM travel.store_travel.travels";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            ClientDto clientDto = new ClientDto();
            clientDto.setId(resultSet.getLong("client_id"));
            clientDto.setName(resultSet.getString("name"));
            clientDto.setSurname(resultSet.getString("surname"));
            return clientDto;
        });
    }

    // Массовый расчет агрегатов TODO

    @Override
    public TravelAggregate getAggregatesByClientId(Long clientId) {
        String sql = "SELECT * FROM travel.store_travel.travels WHERE client_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{clientId}, new BeanPropertyRowMapper<>(TravelAggregate.class));
    }
}
