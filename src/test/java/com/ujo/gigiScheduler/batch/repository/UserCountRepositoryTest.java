package com.ujo.gigiScheduler.batch.repository;

import com.ujo.gigiScheduler.entity.ExitEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserCountRepositoryTest {

    @Autowired
    private ExitRepository exitRepository;

    @Test
    void save() {
        ExitEntity exit = new ExitEntity();
        exit.setStationCode("D12");
        exit.setDate("20230714");
        exit.setUserCount17(567);
        exit.setUserCount18(1234);
        exit.setUserCount19(890);

        int insertCount = exitRepository.save(exit);

        Assertions.assertEquals(1, insertCount);

    }

    @Test
    void findAll() {
    }
}