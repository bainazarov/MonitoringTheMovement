package com.bainazarov.travelproject.TravelProjcet.controller;

import com.bainazarov.travelproject.TravelProjcet.dto.ClientDto;
import com.bainazarov.travelproject.TravelProjcet.entity.Travel;
import com.bainazarov.travelproject.TravelProjcet.entity.TravelAggregate;
import com.bainazarov.travelproject.TravelProjcet.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TravelController {

    private final TravelService travelService;

    @Autowired
    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @PostMapping("/addMove")
    public void addTravel(@RequestBody Travel travel) {
        travelService.addTravel(travel);
    }

    @GetMapping("/clients")
    public List<ClientDto> getAllClients() {
        return travelService.getAllClients();
    }

    // TODO

    @GetMapping("/{clientId}")
    public TravelAggregate getAggregatesByClientId(@PathVariable("clientId") Long clientId) {
        return travelService.getAggregatesByClientId(clientId);
    }
}
