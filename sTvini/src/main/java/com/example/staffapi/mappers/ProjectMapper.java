package com.example.staffapi.mappers;

import com.example.staffapi.dto.ProjectDTO;
import com.example.staffapi.enitity.Project;
import com.example.staffapi.enums.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectMapper {

    public static ProjectDTO toDTO(Project project) {
        ProjectDTO result = new ProjectDTO();

        result.setName(project.getName());
        result.setId(project.getId());

        return result;
    }

    public static List<ProjectDTO> toDTOList(List<Project> projects) {
        return projects.stream().map(ProjectMapper::toDTO).collect(Collectors.toList());
    }

    public static Map<Day, ProjectDTO> toDTOMap(Map<Day, Project> projects) {
        Map<Day, ProjectDTO> res = new HashMap<>();
        for (var curr : projects.keySet()) {
            res.put(curr, toDTO(projects.get(curr)));
        }
        return res;
    }
}
