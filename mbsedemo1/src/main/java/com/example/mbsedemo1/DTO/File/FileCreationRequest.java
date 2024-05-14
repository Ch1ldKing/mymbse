package com.example.mbsedemo1.DTO.File;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileCreationRequest {

    // Getters and Setters
    @NotNull(message = "Folder ID cannot be null")
    private Integer folderId;

    private String content;

    @NotBlank(message = "File name cannot be blank")
    private String name;

}
