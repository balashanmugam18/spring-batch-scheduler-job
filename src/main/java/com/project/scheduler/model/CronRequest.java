package com.project.scheduler.model;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CronRequest {

//    @Schema(description = "orderType", example = "MKT/AMO") //Swagger import
    @NotBlank
    private String jobName;

    @NotBlank
//    @Pattern(regexp = "")CronExpression.parse() -> use in service to validate directly
    private String cronExpression;
}
