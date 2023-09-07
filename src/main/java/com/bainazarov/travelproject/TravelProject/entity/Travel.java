package com.bainazarov.travelproject.TravelProject.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Travel {

    private Long id;
    private Long client_id;
    private String name;
    private String surname;
    private LocalDate birthday;
    private TransportType transport_type;
    private String departure_city_name;
    private String destination_city_name;
    private LocalDateTime departure_time;
    private LocalDateTime destination_time;

}
