package com.project.scheduler.service;

import com.project.scheduler.entity.CronPgEntity;
import com.project.scheduler.exception.DataValidationException;
import com.project.scheduler.model.CronRequest;
import com.project.scheduler.model.CronResponse;
import com.project.scheduler.repository.CronPgEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
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
                validateExistingCronExpression(existing, cronExpression);
                updateCronExpression(jobName, cronExpression, existing);
        } else {
            addCronExpression(jobName, cronExpression);
        }
        Timestamp upcomingSunday = fetchNextRun();
        return new CronResponse().builder().jobName(jobName).cronExpression(cronExpression)
                .detail(existing.isPresent() ? "Cron expression updated successfully." : "New cron expression added successfully.")
                .nextRun(upcomingSunday).build();
    }

    private void validateExistingCronExpression(Optional<CronPgEntity> existing, String cronExpression) {
        String existingCronExpression = existing.get().getCronExpression();
        boolean status = existingCronExpression.equals(cronExpression);
        if (status) {
            throw new DataValidationException("No change required - Identical cron expression already exists in DB.");
        }
    }

    private void validateCronExpression(String cronExpression) {
        try {
            CronExpression.parse(cronExpression);
        } catch (Exception e) {
            log.error("Provided cron expression format : {} is invalid.", cronExpression);
            throw new DataValidationException("Invalid Cron Expression format provided: "+cronExpression);
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

    private Timestamp fetchNextRun() {
        return Timestamp.valueOf(LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)));
    }

    public String getCronExpression(String migrationCron) {
        Optional<CronPgEntity> entity = null;
        try {
            entity = repository.findByJobName(migrationCron);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity.isPresent() ? entity.get().getCronExpression() : "0 */1 * * * ?";
    }

    public List<CronPgEntity> findAllCron() {
        return repository.findAll();
    }
}
