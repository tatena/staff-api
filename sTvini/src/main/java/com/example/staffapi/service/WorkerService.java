package com.example.staffapi.service;

import com.example.staffapi.dto.*;
import com.example.staffapi.enitity.Project;
import com.example.staffapi.enitity.WorkLog;
import com.example.staffapi.enitity.Worker;
import com.example.staffapi.mappers.WorkerMapper;
import com.example.staffapi.repository.WorkerRepository;
import com.example.staffapi.specifications.WorkerSpecifications;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final ProjectService projectService;
    private final WorkLogService workLogService;

    public WorkerService(WorkerRepository workerRepository, ProjectService projectService, WorkLogService workLogService) {
        this.workerRepository = workerRepository;
        this.projectService = projectService;
        this.workLogService = workLogService;
    }

    public Worker save(Worker worker) {
        return workerRepository.save(worker);
    }

    public List<WorkerDTO> searchWithSpecs(WorkersSearchDTO data) {
        String firstName = data.getFirstName();
        String lastName = data.getLastName();

        Specification<Worker> spec = Specification.where(null);

        if (firstName != null && !firstName.isEmpty()) {
            spec = spec.and(WorkerSpecifications.firstNameLike(firstName));
        }

        if (lastName != null && !lastName.isEmpty()) {
            spec = spec.and(WorkerSpecifications.lastNameLike(lastName));
        }

        List<Worker> res = workerRepository.findAll(spec);

        return WorkerMapper.toDTOList(res);
    }

    public WorkerWithProjectsDTO assignWorkToWorker(Long workerId, List<AssignWorkDTO> data) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("WORKER_NOT_FOUND"));
        var projects = worker.getProjects();

        for (var curr : data) {
            Project project = projectService.findById(curr.getProjectId())
                            .orElseThrow(() -> new RuntimeException("PROJECT_NOT_FOUND"));
            projects.put(curr.getDay(), project);
        }
        worker =  workerRepository.save(worker);
        return WorkerMapper.toDTOWithProjects(worker);
    }

    public WorkerWithProjectsDTO getSchedule(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("WORKER_NOT_FOUND"));
        return WorkerMapper.toDTOWithProjects(worker);
    }

    public String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        var authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>)  authentication.getPrincipal();
            return kp.getKeycloakSecurityContext().getToken().getPreferredUsername();
        }
        throw new RuntimeException("USER_NOT_FOUND");
    }

    public WorkerWithProjectsDTO getUser() {
        String username = getCurrentUserLogin();
        Worker worker = workerRepository.findByUsername(username)
                .orElseThrow(() ->new RuntimeException("WORKER_NOT_FOUND"));
        return WorkerMapper.toDTOWithProjects(worker);
    }

    public Long startWorking() {
        String username = getCurrentUserLogin();
        Worker worker = workerRepository.findByUsername(username)
                .orElseThrow(() ->new RuntimeException("WORKER_NOT_FOUND"));
        WorkLog log = new WorkLog();
        log.setWorker(worker);
        log.setStartDate(new Date());
        log = workLogService.save(log);
        return log.getId();
    }

    public void stopWorking(Long logId) {
        WorkLog log = workLogService.findById(logId)
                .orElseThrow(() -> new RuntimeException("LOG_NOT_FOUND"));
        log.setEndDate(new Date());
        workLogService.save(log);
    }

}
