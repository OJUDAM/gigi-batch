package com.ujo.gigiScheduler.batch;

import com.ujo.gigiScheduler.batch.job.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class SubwayScheduler {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private StationJobConfiguration stationJobConfiguration;
    @Autowired
    private StatJobConfiguration statJobConfiguration;
    @Autowired
    private RequestSettingJobConfiguration requestSettingJobConfiguration;
    @Autowired
    private ResetSettingJobConfiguration resetSettingJobConfiguration;
    @Autowired
    private ExitJobConfiguration exitJobConfiguration;

    @Autowired
    private ArrivalRealTimeConfiguration arrivalRealTimeConfiguration;

    @Scheduled(cron = "0/12 * 17-20 * * *")
    public void runArrivalRealTimeJob(){
        //실시간 도착 정보 저장
        this.runJob("Start-Arrival-RealTIme-Batch", arrivalRealTimeConfiguration.realTimeJob());
    }

    @Scheduled(cron = "0 30 2 11 * *")
    public void runStationJob(){
        //지하철 역 정보 입력 배치
        this.runJob("Start-Station-Batch", stationJobConfiguration.stationJob());
    }

    //@Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "0 40 10 * * *")
    public void runStatJob(){
        //지하철 역 이용 통계 자료 입력 배치
        this.runJob("Start-Stat-Batch", statJobConfiguration.statJob());
    }

    //@Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "0 50 10 * * *")
    public void runExitJob(){
        //지하철 역 정보 입력 배치
        this.runJob("Start-Exit-Batch", exitJobConfiguration.exitJob());
    }


/*
    //주요 역들만 매일 배치
   //@Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "0 35 2 * * *")
    public void runRequestSettingJob(){
        //puzzle API 요청 파라미터 입력 배치
        this.runJob("Start-Request-Setting-Batch", requestSettingJobConfiguration.requestSettingJob());
    }

//    @Scheduled(cron = "0 * * * * *")
//    @Scheduled(cron = "0 8 17 * * *")
     @Scheduled(cron = "0 55 2 * * *")
    public void runResetSettingJob(){
        //puzzle API 요청 파라미터 삭제 배치
        this.runJob("Start-Reset-Setting-Batch", resetSettingJobConfiguration.resetSettingJob());
    }
*/
    private void runJob(String message, Job job){
        //job parameter 설정
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate(message, new Date())
                .toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("ERROR TIME : %s", LocalDateTime.now().toString()));;
        }
    }
}
