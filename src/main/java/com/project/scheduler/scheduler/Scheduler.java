package com.project.scheduler.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Scheduler {

    private final Job job;
    private final JobOperator jobOperator;


    public Scheduler(Job job, JobOperator jobOperator) {
        this.job = job;
        this.jobOperator = jobOperator;
    }

    @Scheduled(fixedRate = 5000)
    public void runJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        log.info("Scheduler working");
        jobOperator.start(job, params);
    }

}
