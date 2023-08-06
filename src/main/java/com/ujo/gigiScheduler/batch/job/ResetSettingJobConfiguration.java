package com.ujo.gigiScheduler.batch.job;

import com.ujo.gigiScheduler.batch.repository.RequestStatRepository;
import com.ujo.gigiScheduler.batch.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class ResetSettingJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final StationRepository stationRepository;
    private final RequestStatRepository requestStatRepository;


    @Bean
    public Job resetSettingJob(){
        return jobBuilderFactory.get("resetSettingJob")
                .start(deleteRequestStatStep())
                .next(updateResetBatchStep())
                .build();
    }


    /**
     * STEP 1.API 요청할 지하철 역 정보 초기화
     * */
    @Bean
    public Step deleteRequestStatStep(){
        return stepBuilderFactory.get("deleteRequestStatStep")
                .tasklet((stepContribution, chunkContext) -> {
                    //REQUEST_STAT 데이터모두 삭체
                    int deleteCount = requestStatRepository.deleteAll();

                    log.debug("REQUEST_STAT TABLE " + deleteCount + "건 삭제 완료");

                    return RepeatStatus.FINISHED;
                }).build();
    }

    /**
     * STEP 2.지하철 역 모두 조회하면 다시 처음부터 조회하기 위해 배치 플래그 초기화
     * */
    @Bean
    public Step updateResetBatchStep(){
        return stepBuilderFactory.get("updateResetBatchStep")
                .tasklet((stepContribution, chunkContext) -> {
                    //REQUEST_STAT 데이터모두 삭체
                    int batchFlagCount = stationRepository.countBatchFlag();

                    //아직 조회할 데이터 남아있으면 종료
                    if (batchFlagCount > 0) {
                        log.debug("지하철 통계 조회 " + batchFlagCount + "건 남음");
                        return RepeatStatus.FINISHED;
                    }

                    //조회할 데이터 없으면 배치 플래그 1로 초기화
                    batchFlagCount = stationRepository.updateResetBatchFlag();

                    log.debug("지하철 역 배치 플래그 " + batchFlagCount + "건 초기화 완료");

                    return RepeatStatus.FINISHED;
                }).build();
    }

}
