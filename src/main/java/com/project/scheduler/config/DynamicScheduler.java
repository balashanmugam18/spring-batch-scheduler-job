package com.project.scheduler.config;

import com.project.scheduler.service.CronService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@Configuration
public class DynamicScheduler implements SchedulingConfigurer {

    private final Job job;
    private final JobOperator jobOperator;
    private final CronService cronService;

    public DynamicScheduler(Job job, JobOperator jobOperator, CronService cronService) {
        this.job = job;
        this.jobOperator = jobOperator;
        this.cronService = cronService;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        try {
            taskRegistrar.addTriggerTask(runnableJob(), buildTriggerContext());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Runnable runnableJob() {
        return () -> {
            JobParameters params = buildWeeklyParams();
            try {
                jobOperator.start(job, params);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private JobParameters buildWeeklyParams() {
        LocalDate monday = LocalDate.now().minusDays(6);
        LocalDate sunday = LocalDate.now(); //fetches currentDate as sunday
        log.info("Weekly sync: {} to {} (Mon-Sun)", monday, sunday);
        return new JobParametersBuilder()
                .addString("startDate", monday.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .addString("endDate", sunday.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
    }

    private Trigger buildTriggerContext() {
        return triggerContext -> {
//            Always in DB saved as .toUpperCase(Locale.ROOT) so send as MIGRATIONJOB
            String cron = cronService.getCronExpression("MIGRATIONJOB");
//            String cron = "0 */1 * * * ?"; // testing
            return new CronTrigger(cron).nextExecution(triggerContext);
        };
    }
}
