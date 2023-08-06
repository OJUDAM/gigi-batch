package com.ujo.gigiScheduler.batch.job;

import com.ujo.gigiScheduler.entity.RequestStatEntity;
import com.ujo.gigiScheduler.batch.mapper.RequestStatMapper;
import com.ujo.gigiScheduler.entity.StationEntity;
import com.ujo.gigiScheduler.batch.repository.StationRepository;
import com.ujo.gigiScheduler.common.constants.StatConstant;
import com.ujo.gigiScheduler.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RequestSettingJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RequestStatMapper requestStatMapper;
    private final StationRepository stationRepository;

    @Autowired
    public SqlSessionFactory sqlSessionFactory;

    private final int CHUNK_SIZE = 10;
    @Bean
    public Job requestSettingJob(){
        return jobBuilderFactory.get("requestSettingJob")
                .start(insertRequestSettingStep())
                .next(updateRequestSettingStep())
                .build();
    }


    /**
     * STEP 1.API 요청할 지하철 역 정보 DB 에서 조회하여 REQUEST_STAT 테이블에 저장
     * */
    @Bean
    @JobScope
    public Step insertRequestSettingStep(){
        return stepBuilderFactory.get("insertRequestSettingStep")
                .<RequestStatEntity, RequestStatEntity>chunk(CHUNK_SIZE)
                .reader(new CustomItemReader<>(requestStatMapper.stationsToRequestList(stationRepository.findAllAsRowNum(20))))
                .processor(compositeSettingProcessor())
                .writer(requestSettingWriter())
                .build();
    }

    @Bean
    @StepScope
    public CompositeItemProcessor compositeSettingProcessor(){
        List<ItemProcessor> delegates = new ArrayList<>(1);
        delegates.add(requestSettingProcessor());

        CompositeItemProcessor processor = new CompositeItemProcessor();
        processor.setDelegates(delegates);
        return processor;
    }
    @Bean
    public ItemProcessor<RequestStatEntity, RequestStatEntity> requestSettingProcessor(){
        return item -> {
            //출구 이용자수 요청위해 일주일전 날짜 입력
            item.setRequestDate(DateUtils.addDate("yyyyMMdd", StatConstant.EXIT_PREV_DAYS));
            return item;
        };
    }
    
    @Bean
    @StepScope
    public MyBatisBatchItemWriter<RequestStatEntity> requestSettingWriter(){
        MyBatisBatchItemWriter<RequestStatEntity> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("com.ujo.gigiScheduler.batch.repository.RequestStatRepository.save");
        return writer;
    }

    /**
     * STEP 2. REQUEST 테이블에 저장 후 STATION 플래그 업데이트
     * */
    @Bean
    public Step updateRequestSettingStep(){
        return stepBuilderFactory.get("updateRequestSettingStep")
                .<RequestStatEntity, StationEntity>chunk(CHUNK_SIZE)
                .reader(updateBatchFlagReader())
                .writer(updateBatchFlagWriter())
                .build();
    }
    @Bean
    @StepScope
    public MyBatisPagingItemReader<RequestStatEntity> updateBatchFlagReader(){

        MyBatisPagingItemReader<RequestStatEntity> requestStatReader = new MyBatisPagingItemReader<>();
        requestStatReader.setPageSize(CHUNK_SIZE);
        requestStatReader.setSqlSessionFactory(sqlSessionFactory);
        requestStatReader.setQueryId("com.ujo.test.batch.repository.RequestStatRepository.findStationCodes");

        return requestStatReader;
    }

    @Bean
    @StepScope
    public MyBatisBatchItemWriter<StationEntity> updateBatchFlagWriter(){
        //ItemWriter 생성
        MyBatisBatchItemWriter<StationEntity> writer = new MyBatisBatchItemWriter<>();

        //파라미터 설정
        createItemToParameterMapConverter();

        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("com.ujo.test.batch.repository.StationRepository.updateBatchFlag");
        return writer;
    }

    private  <T> Converter<RequestStatEntity, Map<String, Object>> createItemToParameterMapConverter() {
        return item -> {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("stationCode", item.getStationCode());
            return parameter;
        };
    }
}
