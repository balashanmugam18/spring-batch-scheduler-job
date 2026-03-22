package com.project.scheduler.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cron_expression")
public class CronPgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "cron_expression")
    private String cronExpression;


    @Column(name = "insert_time_stamp")
    private Timestamp insertTimeStamp;

    @Column(name = "update_time_stamp")
    private Timestamp updateTimeStamp;

}
