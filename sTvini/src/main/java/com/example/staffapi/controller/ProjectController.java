package com.example.staffapi.controller;

import com.example.staffapi.dto.ProjectDTO;
import com.example.staffapi.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/project")
@Tag(name = "projects")
@PreAuthorize("hasAuthority(\"manager\")")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectDTO>> getAll() {
        return new ResponseEntity<>(projectService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody String name) {
        return new ResponseEntity<>(projectService.createProject(name), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<ProjectDTO> editProject(@RequestBody ProjectDTO data) {
        return new ResponseEntity<>(projectService.editProject(data), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
