package com.bainazarov.travelproject.TravelProject.service;

import com.bainazarov.travelproject.TravelProject.dto.ClientDto;
import com.bainazarov.travelproject.TravelProject.entity.Travel;
import com.bainazarov.travelproject.TravelProject.entity.TravelAggregate;

import java.util.List;

public interface TravelService {

    void addTravel(Travel travel);
    List<ClientDto> getAllClients();
    TravelAggregate getAggregatesByClientId(Long clientId);
    void calculateAggregatesForAllClients();
}
