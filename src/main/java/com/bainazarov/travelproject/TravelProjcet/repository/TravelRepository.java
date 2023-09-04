package com.bainazarov.travelproject.TravelProjcet.repository;

import com.bainazarov.travelproject.TravelProjcet.dto.ClientDto;
import com.bainazarov.travelproject.TravelProjcet.entity.Travel;
import com.bainazarov.travelproject.TravelProjcet.entity.TravelAggregate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TravelRepository {

    void addTravel(Travel travel);

    List<ClientDto> getAllClients();

    TravelAggregate getAggregatesByClientId(Long clientId);

}
