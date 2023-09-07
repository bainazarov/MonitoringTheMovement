package com.bainazarov.travelproject.TravelProject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelAggregate {

    private Long client_id;
    private Integer cnt_all_trans;
    private Integer cnt_all_trans_1year;
    private Integer cnt_all_trans_5years;
    private Integer cnt_all_trans_before18yo;
    private Integer cnt_all_trans_after18yo;
    private Integer max_cnt_of_days_in_same_place;
    private Integer min_cnt_of_days_in_same_place;
    private Double avg_cnt_of_days_in_same_place;
    private Integer cnt_all_trans_car;
    private Integer cnt_all_trans_bus;
    private Integer cnt_all_trans_plane;
    private Integer cnt_all_trans_train;
}
