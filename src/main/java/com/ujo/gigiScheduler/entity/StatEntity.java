package com.ujo.gigiScheduler.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class StatEntity extends BaseEntity{

    private String stationCode;
    private String day;
    private String hour;
    private int congestionMin00;
    private int congestionMin10;
    private int congestionMin20;
    private int congestionMin30;
    private int congestionMin40;
    private int congestionMin50;
    private int upDnLine;
    private int directAt;
    private String prevStationCode;
    private String startStationCode;
    private String endStationCode;
    private String startDate;
    private String endDate;

    public static StatEntity from(Map<String, Object> statMap) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(statMap, StatEntity.class);
    }
}
