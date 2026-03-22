package com.project.scheduler.service;

import com.project.scheduler.entity.CronPgEntity;
import com.project.scheduler.model.CronRequest;
import com.project.scheduler.model.CronResponse;
import com.project.scheduler.repository.CronPgEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class CronService {

    private final CronPgEntityRepository repository;

    public CronService(CronPgEntityRepository repository) {
        this.repository = repository;
    }

    public CronResponse upsertCron(CronRequest cronRequest) {
        String jobName = cronRequest.getJobName().toUpperCase(Locale.ROOT);
        String cronExpression = cronRequest.getCronExpression();
        validateCronExpression(cronExpression);
        Optional<CronPgEntity> existing = repository.findByJobName(jobName);
        if (existing.isPresent()) {
            updateCronExpression(jobName, cronExpression, existing);
        } else {
            addCronExpression(jobName, cronExpression);
        }
        String upcomingSunday = fetchNextRun(cronExpression);
        return new CronResponse().builder().jobName(jobName).cronExpression(cronExpression)
                .detail(existing.isPresent() ? "Cron expression updated successfully." : "New cron expression added successfully.")
                .nextRun(String.valueOf(upcomingSunday)).build();
    }

    private void validateCronExpression(String cronExpression) {
        try {
            CronExpression.parse(cronExpression);
        } catch (Exception e) {
            log.error("Provided cron expression format : {} is invalid.", cronExpression);
//            throw new BadRequestException("Provided cron expression format : " + expression + "is invalid.");//Add Global Exception + also add spring security
        }
    }

    private void addCronExpression(String jobName, String cronExpression) {
        CronPgEntity entity = new CronPgEntity().builder().jobName(jobName).cronExpression(cronExpression).insertTimeStamp(Timestamp.from(Instant.now())).updateTimeStamp(Timestamp.from(Instant.now())).build();
        repository.save(entity);
    }

    private void updateCronExpression(String jobName, String cronExpression, Optional<CronPgEntity> existing) {
        CronPgEntity entity = existing.get();
        entity.setCronExpression(cronExpression);
        entity.setUpdateTimeStamp(Timestamp.from(Instant.now()));
        repository.save(entity);
    }

    private String fetchNextRun(String cronExpression) {
        return String.valueOf(CronExpression.parse(cronExpression).next(LocalDateTime.now()));
    }


    public String getCronExpression(String migrationcron) {
        Optional<CronPgEntity> entity = repository.findByJobName(migrationcron);
        if (entity.isPresent()) {
            log.info("Available in DB {}", entity.get().getCronExpression());
        } else {
            log.info("Not Available in DB so set as : 0 */1 * * * ?");
        }
        return entity.isPresent() ? entity.get().getCronExpression() : "0 */1 * * * ?";
    }
}
