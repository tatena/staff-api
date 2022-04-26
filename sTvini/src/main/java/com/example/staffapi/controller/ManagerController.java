package com.example.staffapi.controller;

import com.example.staffapi.dto.*;
import com.example.staffapi.service.ManagerService;
import com.example.staffapi.service.WorkLogService;
import com.example.staffapi.service.WorkerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController()
@RequestMapping("/manager")
@Tag(name = "manager")
@PreAuthorize("hasAuthority(\"manager\")")
public class ManagerController {

    private final WorkerService workerService;
    private final ManagerService keycloakService;
    private final WorkLogService workLogService;

    public ManagerController(WorkerService workerService, ManagerService keycloakService, WorkLogService workLogService) {
        this.workerService = workerService;
        this.keycloakService = keycloakService;
        this.workLogService = workLogService;
    }

    @PostMapping("/add-worker")
    public ResponseEntity<WorkerDTO> createWorker(@RequestBody CreateWorkerDTO data) {
        return new ResponseEntity<>(keycloakService.addWorker(data), HttpStatus.OK);
    }

    @PostMapping("/staff")
    public ResponseEntity<List<WorkerDTO>> getAll(@RequestBody WorkersSearchDTO data) {
        return new ResponseEntity<>(workerService.searchWithSpecs(data), HttpStatus.OK);
    }

    @PutMapping("/{workerId}/assign")
    public ResponseEntity<WorkerWithProjectsDTO> assign(
            @PathVariable Long workerId,
            @RequestBody List<AssignWorkDTO> data
    ) {
        return new ResponseEntity<>(workerService.assignWorkToWorker(workerId, data), HttpStatus.OK);
    }

    @GetMapping("/schedule/{workerId}")
    public ResponseEntity<WorkerWithProjectsDTO> getSchedule(
            @PathVariable Long workerId
    ) {
        return new ResponseEntity<>(workerService.getSchedule(workerId), HttpStatus.OK);
    }

    @PostMapping("/report/{date}")
    public ResponseEntity<List<WorkLogDTO>> getReport(
            @RequestParam @DateTimeFormat(pattern = "MM.dd.yyyy") Date date) {
        return new ResponseEntity<>(workLogService.getReport(date), HttpStatus.OK);
    }
}
