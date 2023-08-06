package com.ujo.gigiScheduler.batch.repository;

import com.ujo.gigiScheduler.entity.ExitEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExitRepository {

    @Insert(" INSERT INTO STATION_EXIT_USER_COUNT( STATION_CODE, DATE, USER_COUNT_17" +
            " , USER_COUNT_18, USER_COUNT_19 )" +
            " VALUES( #{stationCode}, #{date}, #{userCount17}" +
            " , #{userCount18}, #{userCount19} )" +
            " ON DUPLICATE KEY UPDATE UPDATED_AT = NOW(), USER_COUNT_17 = #{userCount17}" +
            " , USER_COUNT_18 = #{userCount18}, USER_COUNT_19 = #{userCount19}")
    int save(ExitEntity exitEntity);

    @Select(" SELECT * FROM STATION_EXIT_USER_COUNT")
    List<ExitEntity> findAll();
}
