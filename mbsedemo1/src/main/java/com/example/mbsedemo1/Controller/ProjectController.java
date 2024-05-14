package com.example.mbsedemo1.Controller;

import com.example.mbsedemo1.DTO.ProjectCreationRequest;
import com.example.mbsedemo1.Entity.FileorFolder;
import com.example.mbsedemo1.Entity.Project;
import com.example.mbsedemo1.Service.ProjectService;
import com.example.mbsedemo1.Service.ProjectStructureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.mbsedemo1.Service.ProjectStructureService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProject(@RequestBody @Valid ProjectCreationRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        // 设置其他默认属性或从request中获取其他属性
        projectService.createProject(project);

        return new ResponseEntity<>("Project '" + request.getName() + "' created successfully", HttpStatus.CREATED);
    }

    @Autowired
    private ProjectStructureService projectStructureService;

    @GetMapping("/{projectId}/structure")
    public ResponseEntity<List<FileorFolder>> getProjectStructure(@PathVariable Integer projectId) {
        List<FileorFolder> structure = projectStructureService.getProjectStructure(projectId);
        if (structure.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(structure);
    }
    // 其他与项目相关的操作...
}
