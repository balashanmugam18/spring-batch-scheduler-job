package com.project.scheduler.batch;

import com.project.scheduler.entity.RecordsDocument;
import com.project.scheduler.entity.RecordsPgEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.infrastructure.item.ItemProcessor;

@Slf4j
public class RecordProcessor implements ItemProcessor<RecordsPgEntity, RecordsDocument> {

    @Override
    public RecordsDocument process(RecordsPgEntity item) throws Exception {
        log.info("processor working");
        return RecordsDocument.builder().id(String.valueOf(item.getId())).recordName(item.getRecord_name()).description(item.getDescription()).recordPublishDate(item.getRecord_publish_date()).build();
    }
}
