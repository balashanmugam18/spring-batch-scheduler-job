package com.project.scheduler.config;

import com.project.scheduler.batch.RecordProcessor;
import com.project.scheduler.entity.RecordsDocument;
import com.project.scheduler.entity.RecordsPgEntity;
import com.project.scheduler.repository.RecordsPgEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
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
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Map;

@Configuration
@Slf4j
public class BatchConfig {

    private final RecordsPgEntityRepository pgRepository;
    private final MongoOperations mongoOperations;

    public BatchConfig(RecordsPgEntityRepository pgRepository, MongoOperations mongoOperations) {
        this.pgRepository = pgRepository;
        this.mongoOperations = mongoOperations;
    }


    @Bean
    public Job batchjob(JobRepository jobRepository){
        return new JobBuilder("Update Job", jobRepository)
//                .incrementer() -> use in scheduler
                .start(chunkStep(jobRepository))
                .build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository){
        return new StepBuilder("chunk", jobRepository)
                .<RecordsPgEntity, RecordsDocument>chunk(1000)
                .reader(postgresReader())
                .processor(processor())
                .writer(mongoWriter())
                .build();

    }

    @Bean
    @StepScope
    public RepositoryItemReader<RecordsPgEntity> postgresReader() {
        return new RepositoryItemReaderBuilder<RecordsPgEntity>()
                .name("Postgres Job Reader")
                .repository(pgRepository)
                .methodName("findAll")
                .pageSize(1000)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<RecordsPgEntity, RecordsDocument> processor() {
        return new RecordProcessor();
    }

    @Bean
    @StepScope
    public MongoItemWriter<RecordsDocument> mongoWriter() {
        return new MongoItemWriterBuilder<RecordsDocument >().template(mongoOperations).collection("records").build();
    }


}
