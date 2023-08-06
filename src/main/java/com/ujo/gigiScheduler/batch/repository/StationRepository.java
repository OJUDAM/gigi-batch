package com.ujo.gigiScheduler.batch.repository;

import com.ujo.gigiScheduler.entity.StationEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StationRepository {

    @Insert(" INSERT INTO STATION(STATION_CODE, STATION_NAME, SUBWAY_LINE) " +
            " VALUES(#{stationCode}, #{stationName}, #{subwayLine})" +
            " ON DUPLICATE KEY UPDATE UPDATED_AT = NOW()")
    int save(StationEntity station);

    @Select("SELECT * FROM STATION WHERE STATION_CODE = #{code}")
    StationEntity findAllByCode(@Param("code") String code);

    @Select(" SELECT * FROM STATION WHERE IS_BATCHABLE = 1" +
            " ORDER BY STATION_CODE" +
            " LIMIT 0, #{rowNum}")
    List<StationEntity> findAllAsRowNum(@Param("rowNum")int rowNum);

    @Update(" UPDATE STATION" +
            " SET IS_BATCHABLE = 0" +
            " ,UPDATED_AT = NOW()" +
            " WHERE STATION_CODE = #{stationCode}")
    int updateBatchFlag(@Param("stationCode") String stationCode);

    @Update(" UPDATE STATION" +
            " SET IS_BATCHABLE = 1" +
            " ,UPDATED_AT = NOW()")
    int updateResetBatchFlag();

    @Select(" SELECT COUNT(STATION_CODE) FROM STATION" +
            " WHERE IS_BATCHABLE = 1")
    int countBatchFlag();
}
