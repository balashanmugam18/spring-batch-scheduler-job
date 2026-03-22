package com.project.scheduler.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CronResponse {


    //    @Schema(description = "orderType", example = "MKT/AMO") //Swagger import
    private String jobName;

//    @Pattern(regexp = "")CronExpression.parse() -> use in service to validate directly
    private String cronExpression;

    private String detail;

    private String nextRun;

}
