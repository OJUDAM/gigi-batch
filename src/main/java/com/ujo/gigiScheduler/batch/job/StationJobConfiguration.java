package com.ujo.gigiScheduler.batch.job;

import com.ujo.gigiScheduler.entity.StationEntity;
import com.ujo.gigiScheduler.batch.mapper.StationMapper;
import com.ujo.gigiScheduler.common.utils.apiUtils.PuzzleApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class StationJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PuzzleApi puzzleApi;
    private final StationMapper stationMapper;
    @Autowired
    public SqlSessionFactory sqlSessionFactory;

    @Bean
    public Job stationJob(){
        return jobBuilderFactory.get("stationJob")
                .start(insertStationStep())    //호출 결과로부터
                .build();
    }


    /**
     * STEP 1.지하철 역 정보 조회하여 리스트로 변환
     * */
    @Bean
    @StepScope
    public Step insertStationStep(){
        return stepBuilderFactory.get("insertStationStep")
                .<StationEntity, StationEntity>chunk(10)
                .reader(new CustomItemReader<>(stationMapper.jsonToList(puzzleApi.callMetaInfoApi())))
                .writer(writer())
                .build();
    }

    /**
     * STEP 2.지하철 역 정보 리스트 STATION 테이블에 저장
     * */
    @Bean
    @StepScope
    public MyBatisBatchItemWriter<StationEntity> writer(){
        MyBatisBatchItemWriter<StationEntity> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("com.ujo.gigiScheduler.batch.repository.StationRepository.save");
        return writer;
    }
}
