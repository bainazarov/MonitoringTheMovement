package com.bainazarov.travelproject.TravelProject.repository;
import com.bainazarov.travelproject.TravelProject.dto.ClientDto;
import com.bainazarov.travelproject.TravelProject.entity.Travel;
import com.bainazarov.travelproject.TravelProject.entity.TravelAggregate;
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

    @Override
    public TravelAggregate getAggregatesByClientId(Long clientId) {
        String sql = "SELECT * FROM travel.store_travel.travel_aggregates WHERE client_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{clientId}, new BeanPropertyRowMapper<>(TravelAggregate.class));
    }

    @Override
    public List<Travel> getAllTravels() {
        String sql = "SELECT * FROM travel.store_travel.travels";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Travel.class));
    }

    @Override
    public void upsertAggregate(TravelAggregate aggregate) {
        String sql = "INSERT INTO travel.store_travel.travel_aggregates (client_id, cnt_all_trans, cnt_all_trans_1year, cnt_all_trans_5years, " +
                "cnt_all_trans_before18yo, cnt_all_trans_after18yo, max_cnt_of_days_in_same_place, min_cnt_of_days_in_same_place, avg_cnt_of_days_in_same_place, " +
                "cnt_all_trans_car, cnt_all_trans_bus, cnt_all_trans_plane, cnt_all_trans_train) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (client_id) DO UPDATE SET " +
                "cnt_all_trans = excluded.cnt_all_trans, " +
                "cnt_all_trans_1year = excluded.cnt_all_trans_1year, " +
                "cnt_all_trans_5years = excluded.cnt_all_trans_5years, " +
                "cnt_all_trans_before18yo = excluded.cnt_all_trans_before18yo, " +
                "cnt_all_trans_after18yo = excluded.cnt_all_trans_after18yo, " +
                "max_cnt_of_days_in_same_place = excluded.max_cnt_of_days_in_same_place, " +
                "min_cnt_of_days_in_same_place = excluded.min_cnt_of_days_in_same_place, " +
                "avg_cnt_of_days_in_same_place = excluded.avg_cnt_of_days_in_same_place, " +
                "cnt_all_trans_car = excluded.cnt_all_trans_car, " +
                "cnt_all_trans_bus = excluded.cnt_all_trans_bus, " +
                "cnt_all_trans_plane = excluded.cnt_all_trans_plane, " +
                "cnt_all_trans_train = excluded.cnt_all_trans_train";

        jdbcTemplate.update(sql, aggregate.getClient_id(), aggregate.getCnt_all_trans(), aggregate.getCnt_all_trans_1year(),
                aggregate.getCnt_all_trans_5years(), aggregate.getCnt_all_trans_before18yo(), aggregate.getCnt_all_trans_after18yo(),
                aggregate.getMax_cnt_of_days_in_same_place(), aggregate.getMin_cnt_of_days_in_same_place(), aggregate.getAvg_cnt_of_days_in_same_place(),
                aggregate.getCnt_all_trans_car(), aggregate.getCnt_all_trans_bus(), aggregate.getCnt_all_trans_plane(), aggregate.getCnt_all_trans_train());
    }
}
