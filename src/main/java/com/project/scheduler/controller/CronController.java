package com.project.scheduler.controller;

import com.project.scheduler.model.CronRequest;
import com.project.scheduler.model.CronResponse;
import com.project.scheduler.service.CronService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
