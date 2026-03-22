package com.project.scheduler.listener;

import com.project.scheduler.entity.RecordsDocument;
import com.project.scheduler.entity.RecordsPgEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.listener.ChunkListener;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobListener {
    @Bean
    public ChunkListener<RecordsPgEntity, RecordsDocument> migrationChunkListener() {
        return new ChunkListener<RecordsPgEntity, RecordsDocument>() {
            @Override
            public void afterChunk(Chunk<RecordsDocument> chunk) {
                log.info("Wrote {} records to migrationdb", chunk.size());
            }

            @Override
            public void beforeChunk(Chunk<RecordsPgEntity> chunk) {
                log.info("Reading chunk of {} PG records", chunk.size());
            }
        };
    }
}
