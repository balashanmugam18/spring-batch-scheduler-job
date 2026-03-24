package com.project.scheduler.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CronRequest {

    @Schema(description = "Job Name", example = "MIGRATIONJOB")
    @NotBlank
    private String jobName;

    @NotBlank
    @Schema(description = "Cron expression", allowableValues = {"0 */1 * * * ?", "0 0 2 * * SUN", "0 30 9 * * 1-5"})

    private String cronExpression;
}
