package com.bainazarov.travelproject.TravelProject.service;

import com.bainazarov.travelproject.TravelProject.dto.ClientDto;
import com.bainazarov.travelproject.TravelProject.entity.TransportType;
import com.bainazarov.travelproject.TravelProject.entity.Travel;
import com.bainazarov.travelproject.TravelProject.entity.TravelAggregate;
import com.bainazarov.travelproject.TravelProject.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
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
                completionService.submit(() -> calculateAggregateForClient(travels))
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

    private TravelAggregate calculateAggregateForClient(List<Travel> travels) {
        long clientId = travels.get(0).getClient_id();
        int cntAllTrans = travels.size();
        int cntAllTrans1year = (int) travels.stream()
                .filter(travel -> travel.getDeparture_time().isAfter(LocalDate.now().minusYears(1).atStartOfDay()))
                .count();
        int cntAllTrans5years = (int) travels.stream()
                .filter(travel -> travel
                        .getDeparture_time()
                        .isAfter(LocalDate.now().minusYears(5).atStartOfDay()))
                .count();
        int cntAllTransBefore18yo = (int) travels.stream()
                .filter(travel -> travel.getBirthday().plusYears(18).isAfter(travel.getDeparture_time().toLocalDate()))
                .count();
        int cntAllTransAfter18yo = (int) travels.stream()
                .filter(travel -> travel.getBirthday().plusYears(18).isBefore(travel.getDeparture_time().toLocalDate()))
                .count();
        int maxCntOfDaysInSamePlace = travels.stream()
                .mapToInt(travel -> (int) Duration.between(travel.getDeparture_time(), travel.getDestination_time()).toDays())
                .max()
                .orElse(0);
        int minCntOfDaysInSamePlace = travels.stream()
                .mapToInt(travel -> (int) Duration.between(travel.getDeparture_time(), travel.getDestination_time()).toDays())
                .min()
                .orElse(0);
        double avgCntOfDaysInSamePlace = travels.stream()
                .mapToInt(travel -> (int) Duration.between(travel.getDeparture_time(), travel.getDestination_time()).toDays())
                .average()
                .orElse(0);
        int cntAllTransCar = (int) travels.stream()
                .filter(travel -> travel.getTransport_type() == TransportType.CAR)
                .count();
        int cntAllTransBus = (int) travels.stream()
                .filter(travel -> travel.getTransport_type() == TransportType.BUS)
                .count();
        int cntAllTransPlane = (int) travels.stream()
                .filter(travel -> travel.getTransport_type() == TransportType.PLANE)
                .count();
        int cntAllTransTrain = (int) travels.stream()
                .filter(travel -> travel.getTransport_type() == TransportType.TRAIN)
                .count();

        return new TravelAggregate(clientId, cntAllTrans, cntAllTrans1year, cntAllTrans5years, cntAllTransBefore18yo,
                cntAllTransAfter18yo, maxCntOfDaysInSamePlace, minCntOfDaysInSamePlace, avgCntOfDaysInSamePlace,
                cntAllTransCar, cntAllTransBus, cntAllTransPlane, cntAllTransTrain);
    }

}
