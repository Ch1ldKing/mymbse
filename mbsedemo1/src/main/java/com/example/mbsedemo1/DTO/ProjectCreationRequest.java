package com.example.mbsedemo1.DTO;

import jakarta.validation.constraints.NotBlank;

public class ProjectCreationRequest {

    @NotBlank(message = "Project name cannot be blank")
    private String name;

    // Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
