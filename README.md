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

## 📈 Development Evolution

**From prototype to production-grade scheduler:**
```
v1.0 → Basic @Scheduled (static cron)
│ "0 0 6 ? * SUN" – Weekly Sunday 6 AM
│
v2.0 → SchedulingConfigurer (fetches cron from DB)
│ Cron expressions stored/retrieved by jobName
│
v3.0 → API-Driven✅ LIVE (dynamic cron)
PUT - /api/scheduler/cron/upsert(add + update)
Zero-downtime cron updates
✅ Updates apply on NEXT RUN - NO restart needed!
```
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

## 📡 Batch Job Details (Legacy – Static Scheduling)
> 🔁 This static cron-based scheduling was used in the initial version and has now been replaced by the **dynamic, DB-driven scheduler**. Kept here only for reference.

- `@Scheduled(cron = "0 0 6 ? * SUN")` – Weekly Sunday 6 AM IST
- **Weekly mode:** Processes Mon–Sun date range


## 🎛️ Dynamic Scheduling (Current)
- Replaces old `@Scheduled` static config
- Uses `DynamicSchedulerConfig` (implements `SchedulingConfigurer`)
- Reads cron from DB based on `jobName`→ Next run calculation
- API:
    - `PUT /api/scheduler/cron/upsert`
    - `GET /api/scheduler/cron`
- Updates apply on NEXT RUN - No restart required!

## 📂 Project Structure
```
src/
├── config/
│  ├── BatchConfig.java # Job/Step beans
|  |  ├──reader/
|  |  |   └── PostgresItemReader
|  |  ├──processer/
│  |  |   └── RecordProcessor.java implements ItemProcessor
|  |  └── writer/
│  |      └── MongoItemWriter
|  └── DynamicSchedulerConfig # implements SchedulingConfigurer - runnableJob, TriggerContext
├── batch/
│       └── RecordProcessor.java
├── listener/
│       └── JobListener.java
├── entity
├── repository
└── resources/
  └── application.yml
```

### 🧪 Key Challenges Solved
- **✅ Date filtering:** BETWEEN :startDate AND :endDate (parameterized)
- **✅ Mongo port conflict:** Custom mongodb://mongodb:1234/...
- **✅ Chunk optimization:** 1000 records/commit (tunable)
- **✅ Dual DB:** Postgres Reader → Mongo Writer
- **✅ Job monitoring:** Spring Batch JobRepository
- **✅ Dynamic cron**: API → DB → Scheduler (zero downtime)
- **✅ Global exceptions**: `@RestControllerAdvice` + custom exceptions

### 🧠 What I Learned
- **Spring Batch lifecycle**: Reader/Processor/Writer pattern mastery
- **Dynamic scheduling**: Custom `DynamicScheduler extends SchedulingConfigurer` - cron from DB
- **Job parameters**: Dynamic `startDate/endDate` injection for generic periods
- **Chunk processing**: Memory-efficient 1000-record commits
- **MongoDB integration**: Custom `MongoItemWriter` + bulk operations
- **Production cron**: **`SchedulingConfigurer`** (DB-driven cron via API)
- **API-driven scheduling**: `PUT /api/scheduler/cron/upsert` - add/update cron expressions
- **DB-driven triggers**: Fetch cron by **JOB_NAME** for next run
- **Validation step**: Pre-processing checks for cron expressions
- **Global error handling**: Custom exceptions + `@RestControllerAdvice`
- **Swagger OpenAPI**: Auto-docs at `/swagger-ui.html`


### 🔧 Access Swagger UI: http://localhost:8080/swagger-ui.html

### 🔜 Upcoming Features
- **Multi-threaded steps** (parallel processing)
- **Export job status** (CSV/PDF reports)
- **Micrometer monitoring** (execution metrics)

### 📌 Status
- **Cron scheduling LIVE.** Periodical migration working. Scaling to multi-threaded next.

## 👨‍💻 Author

**Bala Shanmugam**  
**Java Backend Developer**

## ⭐ **Star this repo if you find it helpful!** 🚀