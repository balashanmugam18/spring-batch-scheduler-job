package com.project.scheduler.controller;

import com.project.scheduler.entity.CronPgEntity;
import com.project.scheduler.model.CronRequest;
import com.project.scheduler.model.CronResponse;
import com.project.scheduler.service.CronService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scheduler/cron")
public class CronController {

    private final CronService cronService;

    public CronController(CronService cronService) {
        this.cronService = cronService;
    }

    @PutMapping("/upsert")
    public ResponseEntity<CronResponse> upsertCron(@RequestBody @Valid CronRequest cronRequest) {
        CronResponse response = cronService.upsertCron(cronRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getCron() {
            List<CronPgEntity> response = cronService.findAllCron();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
