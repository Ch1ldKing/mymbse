package com.example.mbsedemo1.Controller;

import com.example.mbsedemo1.DTO.Folder.FolderCreationRequest;
import com.example.mbsedemo1.Entity.Folder;
import com.example.mbsedemo1.Service.FolderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;

@RestController
@Validated
@RequestMapping("/folders")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @GetMapping("/byProject/{projectId}")
    public List<Folder> getFoldersByProject(@PathVariable int projectId) {
        return folderService.getFoldersByProjectId(projectId);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createFolder(@RequestBody @Valid FolderCreationRequest request) {
        Folder folder = new Folder();
        folder.setName(request.getName());
        folder.setProjectId(request.getProjectId());
        folder.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        folderService.createFolder(folder);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Folder '" + request.getName() + "' created successfully for Project ID " + request.getProjectId());
    }

    // 其他与文件夹相关的操作...
}
