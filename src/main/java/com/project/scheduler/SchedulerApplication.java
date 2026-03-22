package com.project.scheduler;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
public class SchedulerApplication {

	public static void main(String[] args) {
		// Set JVM default timezone to IST
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		SpringApplication.run(SchedulerApplication.class, args);
	}

}
