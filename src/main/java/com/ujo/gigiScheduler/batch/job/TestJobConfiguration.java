package com.ujo.gigiScheduler.batch.job;

import com.ujo.gigiScheduler.entity.StatEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TestJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private StatEntity statEntity;

    @Bean
    public Job testJob(){
        return jobBuilderFactory.get("testJob")
                .start(requestStep())   //API 호출
                .on("FAILED")
                .end()
                .from(requestStep())    //호출 결과로부터
                .on("*")    //실패를 제외한 모든 경우
                .to(insertStep())   //DB 입력 단계로 이동 우 실행
                .on("FAILED")
                .end()  //FLOW 종료
                .end()  //job 종료
                .build();
    }

    /**
     * STEP 1.지오비전 퍼즐에서 지하철 통계자료 가져온다
     * */
    @Bean
    public Step requestStep(){
        return stepBuilderFactory.get("testRequestStep")
                .tasklet((stepContribution, chunkContext) -> {
                    log.debug("::::::::::: 배치 스텝 1 :::::::::::");

                    statEntity = new StatEntity();
                    statEntity.setCongestionMin00(140);
                    statEntity.setHour("19");
                    statEntity.setDay("MON");

                    if (statEntity == null) {
                        stepContribution.setExitStatus(ExitStatus.FAILED);
                        return RepeatStatus.FINISHED;
                    }

                    return RepeatStatus.FINISHED;
                }).build();
    }

    /**
     * STEP 2.호출한 데이터 DB에 입력
     * */
    @Bean
    public Step insertStep(){
        return stepBuilderFactory.get("testRequestStep")
                .tasklet((stepContribution, chunkContext) -> {
                    log.debug("::::::::::: 배치 스텝 2 :::::::::::");

                    log.debug(statEntity.toString());

                    return RepeatStatus.FINISHED;
                }).build();
    }

}
