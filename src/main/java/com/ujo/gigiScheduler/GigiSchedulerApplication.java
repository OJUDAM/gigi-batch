package com.ujo.gigiScheduler;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableBatchProcessing
@EnableScheduling
@SpringBootApplication
public class GigiSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GigiSchedulerApplication.class, args);
	}

}
