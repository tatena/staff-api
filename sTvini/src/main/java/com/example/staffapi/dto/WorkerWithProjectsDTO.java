package com.example.staffapi.dto;

import com.example.staffapi.enums.Day;

import java.util.Map;

public class WorkerWithProjectsDTO {
    WorkerDTO workerDTO;
    Map<Day, ProjectDTO> projects;

    public WorkerDTO getWorkerDTO() {
        return workerDTO;
    }

    public void setWorkerDTO(WorkerDTO workerDTO) {
        this.workerDTO = workerDTO;
    }

    public Map<Day, ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(Map<Day, ProjectDTO> projects) {
        this.projects = projects;
    }
}
