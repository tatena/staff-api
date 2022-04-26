package com.example.staffapi.mappers;

import com.example.staffapi.dto.CreateWorkerDTO;
import com.example.staffapi.dto.WorkerDTO;
import com.example.staffapi.dto.WorkerWithProjectsDTO;
import com.example.staffapi.enitity.Worker;

import java.util.List;
import java.util.stream.Collectors;

public class WorkerMapper {

    public static WorkerDTO toDTO(Worker worker) {
        var res = new WorkerDTO();
        res.setFirstName(worker.getFirstName());
        res.setLastName(worker.getLastName());
        res.setPassword(worker.getPassword());
        res.setId(worker.getId());
        res.setUsername(worker.getUsername());

        return res;
    }

    public static Worker fromCreateWorkerDTO(CreateWorkerDTO worker, String password) {
        var res = new Worker();

        res.setFirstName(worker.getFirstName());
        res.setLastName(worker.getLastName());
        res.setPassword(password);
        res.setUsername(worker.getUsername());

        return res;
    }

    public static List<WorkerDTO> toDTOList(List<Worker> workers) {
        return workers.stream().map(WorkerMapper::toDTO).collect(Collectors.toList());
    }

    public static WorkerWithProjectsDTO toDTOWithProjects(Worker worker) {
        var res = new WorkerWithProjectsDTO();
        res.setWorkerDTO(toDTO(worker));
        res.setProjects(ProjectMapper.toDTOMap(worker.getProjects()));
        return res;
    }
}
