package com.ujo.gigiScheduler.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class ArrivalRealTimePositionEntity extends BaseEntity{
    private String trainNo;
    private String trainName;
    private String arrivalStationCode;
    private int arrivalCode;
    private int directAt;
    private int upDnLine;
    private String arrivalDate;

    public static ArrivalRealTimePositionEntity from(Map<String, Object> realTimePositionMap) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(realTimePositionMap, ArrivalRealTimePositionEntity.class);
    }
}
