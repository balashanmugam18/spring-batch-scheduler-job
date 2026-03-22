package com.project.scheduler.config;

import com.project.scheduler.batch.RecordProcessor;
import com.project.scheduler.entity.RecordsDocument;
import com.project.scheduler.entity.RecordsPgEntity;
import com.project.scheduler.listener.JobListener;
import com.project.scheduler.repository.RecordsPgEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.ChunkListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.data.MongoItemWriter;
import org.springframework.batch.infrastructure.item.data.RepositoryItemReader;
import org.springframework.batch.infrastructure.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.infrastructure.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Configuration
@Slf4j
public class BatchConfig {

    private final RecordsPgEntityRepository pgRepository;
    LocalDate monday = LocalDate.now().minusDays(6);
    LocalDate sunday = LocalDate.now();

    public BatchConfig(RecordsPgEntityRepository pgRepository) {
        this.pgRepository = pgRepository;
    }

    @Bean
    public Job batchjob(JobRepository jobRepository) {
        return new JobBuilder("Batch Loader Job", jobRepository).start(chunkStep(jobRepository)).build();
    }

    @Bean
    public ChunkListener migrationChunkListener() {
        return new JobListener().migrationChunkListener();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository) {
        return new StepBuilder("chunk", jobRepository).<RecordsPgEntity, RecordsDocument>chunk(1000).reader(postgresReader()).processor(processor()).writer(mongoWriter()).listener(migrationChunkListener()).build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<RecordsPgEntity> postgresReader() {

        Date startDate = Date.from(monday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(sunday.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

        long totalCount = pgRepository.countByRecordPublishDateBetween(startDate, endDate);
        if (totalCount == 0) {
            log.warn("No records Mon({})-Sun({}) - skipping reader", startDate.toInstant(), endDate.toInstant());// Return empty reader (Spring Batch handles gracefully)
        } else {
            log.info("Migrating {} records Mon({})-Sun({})", totalCount, startDate.toInstant(), endDate.toInstant());
        }
        return new RepositoryItemReaderBuilder<RecordsPgEntity>().name("Postgres Job Reader").repository(pgRepository).methodName("findByRecordPublishDateBetween").arguments(startDate, endDate).pageSize(1000).sorts(Map.of("id", Sort.Direction.ASC)).build();
    }

    @Bean
    @StepScope
    public ItemProcessor<RecordsPgEntity, RecordsDocument> processor() {
        return new RecordProcessor();
    }

    @Bean
    @StepScope
    public MongoItemWriter<RecordsDocument> mongoWriter() {
        MongoDatabaseFactory factory = new SimpleMongoClientDatabaseFactory("mongodb://mongodb:1234@localhost:27017/migration?authSource=admin");
        MongoTemplate mongoTemplate = new MongoTemplate(factory);
        return new MongoItemWriterBuilder<RecordsDocument>().template(mongoTemplate).collection("records").build();
    }

}
