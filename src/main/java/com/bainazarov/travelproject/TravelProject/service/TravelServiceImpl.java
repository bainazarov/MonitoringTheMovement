package com.bainazarov.travelproject.TravelProject.service;

import com.bainazarov.travelproject.TravelProject.dto.ClientDto;
import com.bainazarov.travelproject.TravelProject.entity.Travel;
import com.bainazarov.travelproject.TravelProject.entity.TravelAggregate;
import com.bainazarov.travelproject.TravelProject.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class TravelServiceImpl implements TravelService{

    private final TravelRepository travelRepository;

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

    @Override
    public void calculateAggregatesForAllClients() {
        List<Travel> allTravels = travelRepository.getAllTravels();

        Map<Long, List<Travel>> travelsByClient = allTravels.stream()
                .collect(Collectors.groupingBy(Travel::getClient_id));

        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        CompletionService<TravelAggregate> completionService = new ExecutorCompletionService<>(executorService);

        travelsByClient.values().forEach(travels ->
                completionService.submit(() -> CalculateAggregateForClient.calculateAggregateForClient(travels))
        );

        List<TravelAggregate> aggregatesToUpsert = new ArrayList<>();
        for (int i = 0; i < travelsByClient.size(); i++) {
            try {
                Future<TravelAggregate> future = completionService.take();
                TravelAggregate aggregate = future.get();
                if (aggregate != null) {
                    aggregatesToUpsert.add(aggregate);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();

        for (TravelAggregate aggregate : aggregatesToUpsert) {
            travelRepository.upsertAggregate(aggregate);
        }
    }
}
