package com.example.staffapi.dto;

import com.example.staffapi.enums.Day;

public class AssignWorkDTO {

    private Long projectId;

    private Day day;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
