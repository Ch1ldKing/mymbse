package com.example.mbsedemo1.DTO.Folder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FolderCreationRequest {

    @NotNull(message = "Project ID cannot be null")
    private Integer projectId;

    @NotBlank(message = "Folder name cannot be blank")
    private String name;

    // Getters and Setters
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
