package com.bainazarov.travelproject.TravelProjcet.service;

import com.bainazarov.travelproject.TravelProjcet.dto.ClientDto;
import com.bainazarov.travelproject.TravelProjcet.entity.Travel;
import com.bainazarov.travelproject.TravelProjcet.entity.TravelAggregate;

import java.util.List;

public interface TravelService {

    void addTravel(Travel travel);
    List<ClientDto> getAllClients();
    TravelAggregate getAggregatesByClientId(Long clientId);
}
