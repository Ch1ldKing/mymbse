package com.example.mbsedemo1.DTO.File;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileNameUpdateRequest {
    @NotBlank(message = "File name cannot be blank")
    private String newName;

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}