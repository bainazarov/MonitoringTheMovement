package com.bainazarov.travelproject.TravelProject.service;

import com.bainazarov.travelproject.TravelProject.entity.TransportType;
import com.bainazarov.travelproject.TravelProject.entity.Travel;
import com.bainazarov.travelproject.TravelProject.entity.TravelAggregate;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class CalculateAggregateForClient {
    public static TravelAggregate calculateAggregateForClient(List<Travel> travels) {
        long clientId = getFirstClientId(travels);
        int cntAllTrans = getCntAllTrans(travels);
        int cntAllTrans1year = getCntAllTrans1year(travels);
        int cntAllTrans5years = getCntAllTrans5years(travels);
        int cntAllTransBefore18yo = getCntAllTransBefore18yo(travels);
        int cntAllTransAfter18yo = getCntAllTransAfter18yo(travels);
        int maxCntOfDaysInSamePlace = getMaxCntOfDaysInSamePlace(travels);
        int minCntOfDaysInSamePlace = getMinCntOfDaysInSamePlace(travels);
        double avgCntOfDaysInSamePlace = getAvgCntOfDaysInSamePlace(travels);
        int cntAllTransCar = getCntAllTransCar(travels);
        int cntAllTransBus = getCntAllTransBus(travels);
        int cntAllTransPlane = getCntAllTransPlane(travels);
        int cntAllTransTrain = getCntAllTransTrain(travels);

        return TravelAggregate.builder()
                .client_id(clientId)
                .cnt_all_trans(cntAllTrans)
                .cnt_all_trans_1year(cntAllTrans1year)
                .cnt_all_trans_5years(cntAllTrans5years)
                .cnt_all_trans_before18yo(cntAllTransBefore18yo)
                .cnt_all_trans_after18yo(cntAllTransAfter18yo)
                .max_cnt_of_days_in_same_place(maxCntOfDaysInSamePlace)
                .min_cnt_of_days_in_same_place(minCntOfDaysInSamePlace)
                .avg_cnt_of_days_in_same_place(avgCntOfDaysInSamePlace)
                .cnt_all_trans_car(cntAllTransCar)
                .cnt_all_trans_bus(cntAllTransBus)
                .cnt_all_trans_plane(cntAllTransPlane)
                .cnt_all_trans_train(cntAllTransTrain)
                .build();
    }

    private static long getFirstClientId(List<Travel> travels) {
        return travels.get(0).getClient_id();
    }

    private static int getCntAllTrans(List<Travel> travels){
        return travels.size();
    }

    private static int getCntAllTrans1year(List<Travel> travels) {
         return (int) travels.stream()
                .filter(travel -> travel.getDeparture_time().isAfter(LocalDate.now().minusYears(1).atStartOfDay()))
                .count();
    }

    private static int getCntAllTrans5years(List<Travel> travels) {
        return (int) travels.stream()
                .filter(travel -> travel
                        .getDeparture_time()
                        .isAfter(LocalDate.now().minusYears(5).atStartOfDay()))
                .count();
    }

    private static int getCntAllTransBefore18yo (List<Travel> travels) {
        return (int) travels.stream()
                .filter(travel -> travel.getBirthday().plusYears(18).isAfter(travel.getDeparture_time().toLocalDate()))
                .count();
    }

    private static int getCntAllTransAfter18yo(List<Travel> travels) {
        return (int) travels.stream()
                .filter(travel -> travel.getBirthday().plusYears(18).isBefore(travel.getDeparture_time().toLocalDate()))
                .count();
    }

    private static int getMaxCntOfDaysInSamePlace(List<Travel> travels) {
        return travels.stream()
                .mapToInt(travel -> (int) Duration.between(travel.getDeparture_time(), travel.getDestination_time()).toDays())
                .max()
                .orElse(0);
    }

    private static int getMinCntOfDaysInSamePlace(List<Travel> travels) {
        return travels.stream()
                .mapToInt(travel -> (int) Duration.between(travel.getDeparture_time(), travel.getDestination_time()).toDays())
                .min()
                .orElse(0);
    }

    private static double getAvgCntOfDaysInSamePlace(List<Travel> travels) {
        return travels.stream()
                .mapToInt(travel -> (int) Duration.between(travel.getDeparture_time(), travel.getDestination_time()).toDays())
                .average()
                .orElse(0);
    }

    private static int getCntAllTransCar (List<Travel> travels) {
        return (int) travels.stream()
                .filter(travel -> travel.getTransport_type() == TransportType.CAR)
                .count();
    }

    private static int getCntAllTransBus (List<Travel> travels) {
        return (int) travels.stream()
                .filter(travel -> travel.getTransport_type() == TransportType.BUS)
                .count();
    }

    private static int getCntAllTransPlane (List<Travel> travels) {
        return  (int) travels.stream()
                .filter(travel -> travel.getTransport_type() == TransportType.PLANE)
                .count();
    }

    private static int getCntAllTransTrain (List<Travel> travels) {
        return  (int) travels.stream()
                .filter(travel -> travel.getTransport_type() == TransportType.TRAIN)
                .count();
    }
}
