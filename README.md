# 📊 Scheduler Batch Processor – Spring Boot + Spring Batch

![Java-17-orange?style=for-the-badge&logo=java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![SpringBoot-4.0.3-brightgreen?style=for-the-badge&logo=springboot)](https://img.shields.io/badge/SpringBoot-4.0.3-brightgreen?style=for-the-badge&logo=springboot)
![SpringBatch-blue?style=for-the-badge&logo=spring)](https://img.shields.io/badge/Spring%20Batch-blue?style=for-the-badge&logo=spring)
![PostgreSQL-green?style=for-the-badge&logo=postgresql)](https://img.shields.io/badge/PostgreSQL-green?style=for-the-badge&logo=postgresql)
![MongoDB-purple?style=for-the-badge&logo=mongodb)](https://img.shields.io/badge/MongoDB-purple?style=for-the-badge&logo=mongodb)
![Maven-red?style=for-the-badge&logo=apachemaven)](https://img.shields.io/badge/Maven-red?style=for-the-badge&logo=apachemaven)

## 🚀 Overview

Production-grade, **Scalable Spring Batch scheduler** for **generic periodic data processing**. Configurable for **daily, weekly, monthly.** PostgreSQL `publishDate` filtering → **MongoDB migrationdb** migration.
Designed for scheduled runs with configurable date ranges, chunked processing, and fault tolerance.

**Built for enterprise-scale batch jobs** - handles 10K+ records efficiently.

## 🛠 Tech Stack

- **Java 17**
- **Spring Boot 4.0.3** (Batch, Data JPA, Web)
- **Spring Batch** (Job/Step configuration, Chunk processing)
- **PostgreSQL** (Source: publishDate filtering)
- **MongoDB** (Target DB)
- **Maven**
- **Lombok**

## ✅ Features Implemented

### 🔄 Batch Processing Pipeline

- **ItemReader**: PostgreSQL `publishDate` filtering (`daily/weekly` or `periodical range`)
- **ItemProcessor**: Data transformation/validation
- **ItemWriter**: MongoDB `migrationdb` insertion
- **Chunked processing** (1000 records/commit)

### ⏰ Scheduling & Config

- **Dynamic date ranges** (periodic runs)
- **Job parameters** (`startDate`, `endDate`)
- **Restartable jobs** (failed step recovery)

### 🛡️ Fault Tolerance

- **Skip policy** (bad records → log/skip)
- **Retry logic** (transient DB errors)
- **JobRepository** (execution tracking)

### ⏰ **Cron Scheduling** ✅ **LIVE**

## 📡 Batch Job Details
- **@Scheduled(cron = "0 0 6 ? * SUN")** # Weekly Sunday 6 AM IST
- **Weekly mode:** Mon-Sun range


## 📂 Project Structure
```
src/
├── config/
│ └── BatchConfig.java # Job/Step beans
|    ├──reader/
|    │  └── PostgresItemReader
|    ├──processer/
│    |  └── RecordProcessor.java implements ItemProcessor
|    └── writer/
│       └── MongoItemWriter
├── batch/
│ └── RecordProcessor.java
├── scheduler/
│ └── Scheduler.java
├── listener/
│ └── JobListener.java
├── entity
├── repository
└── resources/
  └── application.yml
```

### 🧪 Key Challenges Solved
- **Date filtering:** BETWEEN :startDate AND :endDate (parameterized)
- **Mongo port conflict:** Custom mongodb://mongodb:1234/...
- **Chunk optimization:** 1000 records/commit (tunable)
- **Dual DB:** Postgres Reader → Mongo Writer
- **Job monitoring:** Spring Batch JobRepository

### 🧠 What I Learned
- **Spring Batch lifecycle:** Reader/Processor/Writer pattern
- **Cron scheduling:** Production cron - Timezone-aware scheduling (IST)
- **Job parameters:** Dynamic startDate/endDate injection
- **Chunk processing:** Memory-efficient large datasets
- **MongoDB Spring Data:** Custom MongoItemWriter- Bulk operations
- **Production scheduling:** @EnableBatchProcessing + @EnableScheduling
- **Dynamic Job params:** Date range injection

### 🔜 Upcoming Features
- **Multi-threaded steps** (parallel processing)
- **Validation step** (pre-processing checks)
- **Export job status** (CSV/PDF reports)
- **Micrometer monitoring** (execution metrics)

### 📌 Status
- **Cron scheduling LIVE.** Daily migration working. Scaling to multi-threaded next.

## 👨‍💻 Author

**Bala Shanmugam**  
**Java Backend Developer**

## ⭐ **Star this repo if you find it helpful!** 🚀