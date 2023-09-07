package com.bainazarov.travelproject.TravelProject.repository;

import com.bainazarov.travelproject.TravelProject.dto.ClientDto;
import com.bainazarov.travelproject.TravelProject.entity.Travel;
import com.bainazarov.travelproject.TravelProject.entity.TravelAggregate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TravelRepository {

    void addTravel(Travel travel);
    List<ClientDto> getAllClients();
    TravelAggregate getAggregatesByClientId(Long clientId);
    List<Travel> getAllTravels();
    void upsertAggregate(TravelAggregate aggregate);

}
