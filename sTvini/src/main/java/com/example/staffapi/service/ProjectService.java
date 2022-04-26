package com.example.staffapi.service;

import com.example.staffapi.dto.ProjectDTO;
import com.example.staffapi.enitity.Project;
import com.example.staffapi.mappers.ProjectMapper;
import com.example.staffapi.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectDTO createProject(String name) {
        Project newProject = new Project(name);
        newProject = projectRepository.save(newProject);
        return ProjectMapper.toDTO(newProject);
    }

    public List<ProjectDTO> getAll() {
        var projects = projectRepository.findAll();
        return ProjectMapper.toDTOList(projects);
    }

    public ProjectDTO editProject(ProjectDTO data) {
        Project project = projectRepository.findById(data.getId())
                .orElseThrow(() ->new RuntimeException("PROJECT_NO_FOUND"));
        project.setName(data.getName());
        return ProjectMapper.toDTO(projectRepository.save(project));
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public Optional<Project> findById(Long projectId) {
        return projectRepository.findById(projectId);
    }
}
