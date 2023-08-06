package com.ujo.gigiScheduler.batch.repository;

import com.ujo.gigiScheduler.entity.StationEntity;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Test
    void saveTest(){
        StationEntity station = new StationEntity();
        station.setStationCode("D12");
        station.setStationName("정자역");
        station.setSubwayLine("신분당선");


        int result = stationRepository.save(station);

        int updateResult = stationRepository.save(station);

        System.out.println(result);

        Assert.assertEquals(1 , updateResult);
    }

    @Test
    void findTest(){
        StationEntity station = stationRepository.findAllByCode("D12");

        System.out.println(station.toString());

        Assert.assertEquals("정자역", station.getStationName());
    }

    @Test
    void findAsRowNumTest() {
        List<StationEntity> stations = stationRepository.findAllAsRowNum(20);

        for (StationEntity station : stations) {
            System.out.println(station.toString());
        }

        Assert.assertEquals(20, stations.size());
    }

}