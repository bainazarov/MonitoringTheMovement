package com.bainazarov.travelproject.TravelProjcet.service;

import com.bainazarov.travelproject.TravelProjcet.dto.ClientDto;
import com.bainazarov.travelproject.TravelProjcet.entity.Travel;
import com.bainazarov.travelproject.TravelProjcet.entity.TravelAggregate;
import com.bainazarov.travelproject.TravelProjcet.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TravelServiceImpl implements TravelService{

    private TravelRepository travelRepository;

    @Autowired
    public TravelServiceImpl(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @Override
    public void addTravel(Travel travel) {
        travelRepository.addTravel(travel);
    }

    @Override
    public List<ClientDto> getAllClients() {
        return travelRepository.getAllClients();
    }

    @Override
    public TravelAggregate getAggregatesByClientId(Long clientId) {
        return travelRepository.getAggregatesByClientId(clientId);
    }
}
