package com.ujo.gigiScheduler.batch.repository;

import com.ujo.gigiScheduler.entity.ArrivalRealTimeEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArrivalRealTimeBatchRepository {

    @Insert(" INSERT INTO STATION_ARRIVAL_REALTIME" +
            " ( TRAIN_NO, TRAIN_NAME, ARRIVAL_STATION_CODE" +
            " , ARRIVAL_MESSAGE, TARGET_STATION_CODE, ARRIVAL_CODE" +
            " , DIRECT_AT, UP_DN_LINE, ARRIVAL_DATE,CREATED_AT )" +
            " VALUES" +
            " ( #{trainNo}, #{trainName}, #{arrivalStationCode}" +
            " , #{arrivalMessage}, #{targetStationCode}, #{arrivalCode}" +
            " , #{directAt}, #{upDnLine}, #{arrivalDate}, #{createdAt} )" +
            " ON DUPLICATE KEY UPDATE UPDATED_AT = NOW(), ARRIVAL_MESSAGE = #{arrivalMessage}" +
            " , ARRIVAL_STATION_CODE = #{arrivalStationCode}, ARRIVAL_CODE=#{arrivalCode}, CREATED_AT = #{createdAt}")
    int save(ArrivalRealTimeEntity arrivalRealTimeEntity);
}
