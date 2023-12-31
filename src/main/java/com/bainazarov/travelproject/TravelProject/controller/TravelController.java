package com.bainazarov.travelproject.TravelProject.controller;

import com.bainazarov.travelproject.TravelProject.dto.ClientDto;
import com.bainazarov.travelproject.TravelProject.entity.Travel;
import com.bainazarov.travelproject.TravelProject.entity.TravelAggregate;
import com.bainazarov.travelproject.TravelProject.service.TravelService;
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

    @PostMapping("/calculate-aggregates")
    public void calculateAggregatesForAllClients() {
        long startTime = System.currentTimeMillis();

        travelService.calculateAggregatesForAllClients();

        long endTime = System.currentTimeMillis();

        long result = endTime - startTime;
        System.out.println("Время выполнения: " + result + " миллисекунд");
    }

    @GetMapping("/{clientId}")
    public TravelAggregate getAggregatesByClientId(@PathVariable("clientId") Long clientId) {
        return travelService.getAggregatesByClientId(clientId);
    }
}
