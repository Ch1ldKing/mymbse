package com.example.mbsedemo1.DTO.File;

import jakarta.validation.constraints.NotBlank;

public class FileContentUpdateRequest {
    @NotBlank(message = "File content cannot be blank")
    private String newContent;

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }
}