package com.example.mbsedemo1.Controller;

import com.example.mbsedemo1.Entity.File;
import com.example.mbsedemo1.Entity.FileContent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import com.example.mbsedemo1.Entity.FileorFolder;
import com.example.mbsedemo1.Service.FileorFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fileorfolder")
public class FileorFolderController {

    private final FileorFolderService fileorFolderService;

    @Autowired
    public FileorFolderController(FileorFolderService fileorFolderService) {
        this.fileorFolderService = fileorFolderService;
    }

    @GetMapping("/{id}")
    public FileorFolder getFileorFolderById(@PathVariable Integer id) {
        return fileorFolderService.findById(id);
    }

    @PostMapping("/updateContent")
    public ResponseEntity<FileorFolder> updateFileorFolder(@RequestBody FileorFolder fileorFolder) {
        try {
            fileorFolderService.updateContentById(fileorFolder);
            return new ResponseEntity<>(fileorFolder, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/projectid/{projectId}")
    public ResponseEntity<List<FileorFolder>> getFileorFolderByProjectId(@PathVariable Integer projectId) {
        try {
            List<FileorFolder> fileorFolders = fileorFolderService.findByProjectId(projectId);
            if (fileorFolders.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(fileorFolders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<FileorFolder>> getAllFileorFolder() {
        try {
            List<FileorFolder> fileorFolders = fileorFolderService.getAllFileorFolder();
            if (fileorFolders.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(fileorFolders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/search")
    public List<FileorFolder> searchByContent(@RequestBody FileorFolder fileorFolder) {
        System.out.println(fileorFolder.getContent());
        return fileorFolderService.searchByContent(fileorFolder.getContent());}

    @PostMapping("/check")
    public boolean checkByContent(@RequestBody FileContent fileContent) {
       return fileContent.checked();
    }

}

