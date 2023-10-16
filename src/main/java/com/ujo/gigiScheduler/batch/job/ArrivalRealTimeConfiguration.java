package com.ujo.gigiScheduler.batch.job;

import com.ujo.gigiScheduler.batch.mapper.ArrivalRealTimeMapper;
import com.ujo.gigiScheduler.batch.mapper.ArrivalRealTimePositionMapper;
import com.ujo.gigiScheduler.common.constants.PrimaryStationConstant;
import com.ujo.gigiScheduler.common.utils.apiUtils.SeoulApi;
import com.ujo.gigiScheduler.entity.ArrivalRealTimeEntity;
import com.ujo.gigiScheduler.entity.ArrivalRealTimePositionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ArrivalRealTimeConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SeoulApi seoulApi;
    private final ArrivalRealTimeMapper arrivalRealTimeMapper;
    private final ArrivalRealTimePositionMapper arrivalRealTimePositionMapper;

    @Autowired
    public SqlSessionFactory sqlSessionFactory;

    @Bean
    public Job realTimeJob() {
        return jobBuilderFactory.get("realTimeJob")
                .start(insertArrivalRealTimePositionStep())
//                .start(insertArrivalRealTimeStep())    //호출 결과로부터
//                .next(insertArrivalRealTimePositionStep())
                .build();
    }

    /**
     * STEP 1.망포역 실시간 도착 API 조회
     */
    @Bean
    @JobScope
    public Step insertArrivalRealTimeStep() {
        return stepBuilderFactory.get("insertArrivalRealTimeStep")
                .<ArrivalRealTimeEntity, ArrivalRealTimeEntity>chunk(20)
                .reader(new CustomItemReader<>(arrivalRealTimeMapper.jsonToList(seoulApi.callRealArrivalTimeApi(PrimaryStationConstant.PRIMARY_STATION_MANGPO))))
                .writer(arrivalRealTimeWriter())
                .build();
    }

    /**
     * STEP 2.STATION_ARRIVAL_REALTIME 에 저장
     */
    @Bean
    @StepScope
    public MyBatisBatchItemWriter<ArrivalRealTimeEntity> arrivalRealTimeWriter() {
        MyBatisBatchItemWriter<ArrivalRealTimeEntity> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("com.ujo.gigiScheduler.batch.repository.ArrivalRealTimeBatchRepository.save");
        return writer;
    }

    /**
     * STEP 3.수인 분당선 실시간 위치 API 조회
     */
    @Bean
    @JobScope
    public Step insertArrivalRealTimePositionStep() {
        return stepBuilderFactory.get("insertArrivalRealTimePositionStep")
                .<ArrivalRealTimePositionEntity, ArrivalRealTimePositionEntity>chunk(20)
                .reader(new CustomItemReader<>(arrivalRealTimePositionMapper.jsonToList(seoulApi.callRealTimePositionApi(PrimaryStationConstant.PRIMARY_SUBWAY_LINE_SUINBUNDANG))))
                .writer(arrivalRealTimePositionWriter())
                .build();
    }

    /**
     * STEP 4.STATION_ARRIVAL_REALTIME_POSITION 에 저장
     */
    @Bean
    @StepScope
    public MyBatisBatchItemWriter<ArrivalRealTimePositionEntity> arrivalRealTimePositionWriter() {
        MyBatisBatchItemWriter<ArrivalRealTimePositionEntity> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("com.ujo.gigiScheduler.batch.repository.ArrivalRealTimePositionBatchRepository.save");
        return writer;
    }
}
