package com.example.staffapi.service;

import com.example.staffapi.dto.WorkLogDTO;
import com.example.staffapi.enitity.WorkLog;
import com.example.staffapi.mappers.WorkLogMapper;
import com.example.staffapi.repository.WorkLogRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WorkLogService {
    private final WorkLogRepository workLogRepository;

    public WorkLogService(WorkLogRepository workLogRepository) {
        this.workLogRepository = workLogRepository;
    }

    public WorkLog save(WorkLog workLog) {
        return workLogRepository.save(workLog);
    }

    public Optional<WorkLog> findById(Long id) {
        return workLogRepository.findById(id);
    }

    public List<WorkLogDTO> getReport(Date date) {
        var res = workLogRepository.findAllForDate(date);
        return WorkLogMapper.toDTOList(res);
    }
}
