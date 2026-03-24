package com.project.scheduler.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CronResponse {


    @Schema(description = "Job Name", example = "MIGRATIONJOB")
    private String jobName;
    @Schema(description = "Cron expression", allowableValues = {"0 */1 * * * ?", "0 0 2 * * SUN", "0 30 9 * * 1-5"})
    private String cronExpression;
    @Schema(description = "Detailed Message", example = "New cron expression added successfully.")
    private String detail;
    @Schema(description = "Next Scheduled run Timestamp", example = "2026-03-22T19:36:42.422Z")
    private Timestamp nextRun;

}
