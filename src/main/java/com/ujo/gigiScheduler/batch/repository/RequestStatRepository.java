package com.ujo.gigiScheduler.batch.repository;

import com.ujo.gigiScheduler.entity.RequestStatEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RequestStatRepository {

    @Insert(" INSERT INTO REQUEST_STAT(STATION_CODE, REQUEST_DATE, REQUEST_HOUR) " +
            " VALUES(#{stationCode}, #{requestDate}, #{requestHour})" +
            " ON DUPLICATE KEY UPDATE UPDATED_AT = NOW()")
    int save(RequestStatEntity requestStat);

    @Select("SELECT * FROM REQUEST_STAT")
    List<RequestStatEntity> findAll();

    @Select(" SELECT STATION_CODE, REQUEST_DATE FROM REQUEST_STAT" +
            " GROUP BY STATION_CODE, REQUEST_DATE")
    List<RequestStatEntity> findCodeAndDate();

    @Select(" SELECT STATION_CODE FROM REQUEST_STAT" +
            " GROUP BY STATION_CODE" +
            " LIMIT #{_skiprows}, #{_pagesize}")
    List<RequestStatEntity> findStationCodes(@Param("_skiprows") int skipRows, @Param("_pagesize") int pageSize);

    @Delete("DELETE FROM REQUEST_STAT")
    int deleteAll();
}
