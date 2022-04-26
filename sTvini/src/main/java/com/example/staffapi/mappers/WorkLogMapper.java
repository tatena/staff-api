package com.example.staffapi.mappers;

import com.example.staffapi.dto.WorkLogDTO;
import com.example.staffapi.enitity.WorkLog;

import java.util.List;
import java.util.stream.Collectors;

public class WorkLogMapper {

    public static WorkLogDTO toDTO (WorkLog workLog) {
        WorkLogDTO res = new WorkLogDTO();
        res.setWorkerDTO(WorkerMapper.toDTO(workLog.getWorker()));
        res.setStartDate(workLog.getStartDate());
        res.setEndDate(workLog.getEndDate());

        return res;
    }

    public static List<WorkLogDTO> toDTOList(List<WorkLog> workers) {
        return workers.stream().map(WorkLogMapper::toDTO).collect(Collectors.toList());
    }
}
