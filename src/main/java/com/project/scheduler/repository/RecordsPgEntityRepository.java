package com.project.scheduler.repository;

import com.project.scheduler.entity.RecordsPgEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecordsPgEntityRepository extends JpaRepository<RecordsPgEntity, Integer> {
    Page<RecordsPgEntity> findAll(Pageable pageable);
}
