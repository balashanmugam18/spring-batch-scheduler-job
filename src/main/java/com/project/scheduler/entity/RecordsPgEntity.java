package com.project.scheduler.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "records")
public class RecordsPgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "record_name")
    private String record_name;

    @Column(name = "description")
    private String description;

    @Column(name = "record_publish_date")
    private Date record_publish_date;
}
