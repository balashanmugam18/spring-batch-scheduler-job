package com.project.scheduler.repository;


import com.project.scheduler.entity.CronPgEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CronPgEntityRepository extends JpaRepository<CronPgEntity, Integer> {

    Optional<CronPgEntity> findByJobName(@NotBlank String jobName);
}
