//package com.bainazarov.travelproject.TravelProjcet.service;
//
//import com.bainazarov.travelproject.TravelProjcet.entity.TransportType;
//import com.bainazarov.travelproject.TravelProjcet.entity.Travel;
//import com.github.javafaker.Faker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.concurrent.ThreadLocalRandom;
//
//@Component
//public class DataGenerator implements CommandLineRunner {
//
//    private final TravelService travelService;
//    private final Faker faker;
//
//    @Autowired
//    public DataGenerator(TravelService travelService) {
//        this.travelService = travelService;
//        this.faker = new Faker();
//    }
//    @Override
//    public void run(String... args) {
//       int totalClients = 40000;
//       int movesPerClient = 25;
//
//        generateFakeData(totalClients, movesPerClient);
//    }
//
//    private void generateFakeData(int totalClients, int movesPerClient) {
//        for (int i = 6; i <= totalClients; i++) {
//            int currentClient = i;
//            String name = generateFakeName();
//            String surname = generateFakeSurname();
//            LocalDate birthday = generateFakeBirthday();
//            for (int j = 0; j < movesPerClient; j++) {
//                Travel travel = generateFakeTravel(currentClient);
//                travel.setName(name);
//                travel.setSurname(surname);
//                travel.setBirthday(birthday);
//                travelService.addTravel(travel);
//            }
//        }
//    }
//
//    private Travel generateFakeTravel(int clientId) {
//        Travel travel = new Travel();
//        travel.setClient_id((long) clientId);
//        travel.setName(generateFakeName());
//        travel.setSurname(generateFakeSurname());
//        travel.setBirthday(generateFakeBirthday());
//        travel.setTransport_type(TransportType.values()[faker.random().nextInt(TransportType.values().length)]);
//        travel.setDeparture_city_name(faker.address().city());
//        travel.setDestination_city_name(faker.address().city());
//        travel.setDeparture_time(generateRandomDateTime());
//        travel.setDestination_time(generateRandomDateTime());
//
//        return travel;
//    }
//    private LocalDateTime generateRandomDateTime() {
//        int year = ThreadLocalRandom.current().nextInt(2010, 2023);
//        int month = ThreadLocalRandom.current().nextInt(1, 13);
//        int day = ThreadLocalRandom.current().nextInt(1, 29);
//        int hour = ThreadLocalRandom.current().nextInt(0, 24);
//        int minute = ThreadLocalRandom.current().nextInt(0, 60);
//
//        return LocalDateTime.of(year, month, day, hour, minute);
//    }
//
//    private String generateFakeName() {
//        return faker.name().firstName();
//    }
//
//    private String generateFakeSurname() {
//        return faker.name().lastName();
//    }
//    private LocalDate generateFakeBirthday() {
//        return faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//    }
//}