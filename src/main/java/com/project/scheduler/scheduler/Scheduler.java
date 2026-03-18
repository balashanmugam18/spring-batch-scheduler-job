package com.project.scheduler.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class Scheduler {

    private final Job job;
    private final JobOperator jobOperator;

    public Scheduler(Job job, JobOperator jobOperator) {
        this.job = job;
        this.jobOperator = jobOperator;
    }

    @Scheduled(cron = "0 */1 * * * ?")  // Every Sunday 6AM @Scheduled(cron = "0 0 6 ? * SUN")every month ->@Scheduled(cron = "0 */1 * * * ?")  // Every minute (test)
    public void runJob() throws Exception {
        LocalDate monday = LocalDate.now().minusDays(6);
        LocalDate sunday = LocalDate.now();
        JobParameters params = new JobParametersBuilder()
                .addString("startDate", monday.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .addString("endDate", sunday.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        log.info("Weekly sync: {} to {} (Mon-Sun)", monday, sunday);
        jobOperator.start(job, params);
    }

}
