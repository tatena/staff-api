package com.example.staffapi.controller;

import com.example.staffapi.dto.WorkerWithProjectsDTO;
import com.example.staffapi.service.WorkerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/worker")
@Tag(name = "worker")
@PreAuthorize("hasAuthority(\"worker\")")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping()
    public ResponseEntity<WorkerWithProjectsDTO> getAll() {
        return new ResponseEntity<>(workerService.getUser(), HttpStatus.OK);
    }

    @PostMapping("/start")
    public ResponseEntity<Long> startWork() {
        return new ResponseEntity<>(workerService.startWorking(), HttpStatus.OK);
    }

    @PostMapping("/stop/{logId}")
    public ResponseEntity<Void> endWork(
            @PathVariable Long logId
    ) {
        workerService.stopWorking(logId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
