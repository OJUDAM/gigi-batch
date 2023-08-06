package com.ujo.gigiScheduler.batch.repository;

import com.ujo.gigiScheduler.entity.RequestStatEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class RequestStatRepositoryTest {

    @Autowired
    private RequestStatRepository requestStatRepository;

    @Test
    @DisplayName("요청할 파라미터 DB 에서 조회함")
    void findAllTest() {
        List<RequestStatEntity> reqStatList = requestStatRepository.findAll();

        for (RequestStatEntity entity : reqStatList) {
            entity.toString();
        }
        Assertions.assertEquals(6, reqStatList.size());
    }
}