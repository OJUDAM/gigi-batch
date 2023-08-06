package com.ujo.gigiScheduler.batch.repository;

import com.ujo.gigiScheduler.entity.StatEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StatRepository {

    @Insert(" INSERT INTO STATION_STAT( STATION_CODE, DAY, HOUR" +
            " ,CONGESTION_MIN_00, CONGESTION_MIN_10, CONGESTION_MIN_20 " +
            " ,CONGESTION_MIN_30, CONGESTION_MIN_40, CONGESTION_MIN_50" +
            " ,UP_DN_LINE, DIRECT_AT, PREV_STATION_CODE " +
            " ,START_STATION_CODE, END_STATION_CODE, START_DATE " +
            " ,END_DATE )" +
            " VALUES( #{stationCode}, #{day}, #{hour}" +
            " ,#{congestionMin00}, #{congestionMin10}, #{congestionMin20}" +
            " ,#{congestionMin30}, #{congestionMin40}, #{congestionMin50}" +
            " ,#{upDnLine}, #{directAt}, #{prevStationCode} " +
            " ,#{startStationCode}, #{endStationCode}, #{startDate} " +
            " ,#{endDate} )" +
            " ON DUPLICATE KEY UPDATE UPDATED_AT = NOW()")
    int save(StatEntity stat);


    @Select("SELECT * FROM STATION_STAT WHERE STATION_CODE = #{code}")
    StatEntity findAllByCode(@Param("code") String code);

}
