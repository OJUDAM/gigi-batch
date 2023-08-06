package com.ujo.gigiScheduler.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class ExitEntity extends BaseEntity{
    private String stationCode;
    private String date;
    private int userCount17;
    private int userCount18;
    private int userCount19;

    public static ExitEntity from(Map<String, Object> countMap) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(countMap, ExitEntity.class);
    }
}
