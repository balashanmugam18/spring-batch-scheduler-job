package com.project.scheduler.repository;

import com.project.scheduler.entity.RecordsPgEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface RecordsPgEntityRepository extends JpaRepository<RecordsPgEntity, Integer> {

    Page<RecordsPgEntity> findAll(Pageable pageable);
    Page<RecordsPgEntity> findByRecordPublishDateBetween(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT COUNT(e) FROM RecordsPgEntity e WHERE e.recordPublishDate BETWEEN :start AND :end")
    long countByRecordPublishDateBetween(Date start, Date end);
}
