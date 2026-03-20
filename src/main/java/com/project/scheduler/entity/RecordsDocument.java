package com.project.scheduler.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "records")
public class RecordsDocument {

    @Id
    private String id;

    @Field("record_name")
    private String recordName;

    @Field("description")
    private String description;

    @Field("record_publish_date")
    private Date recordPublishDate;

}
