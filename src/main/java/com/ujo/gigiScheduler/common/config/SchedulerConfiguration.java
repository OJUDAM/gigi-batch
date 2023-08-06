package com.ujo.gigiScheduler.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulerConfiguration implements SchedulingConfigurer {
        private final int POOL_SIZE = 2;

        @Override
        public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
            ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

            threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
            threadPoolTaskScheduler.setThreadNamePrefix("Puzzle-Scheduler-");
            threadPoolTaskScheduler.initialize();

            taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);

        }

}
