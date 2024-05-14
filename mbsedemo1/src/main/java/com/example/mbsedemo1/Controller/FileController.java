package com.example.mbsedemo1.Controller;

import com.example.mbsedemo1.DTO.File.FileContentUpdateRequest;
import com.example.mbsedemo1.DTO.File.FileCreationRequest;
import com.example.mbsedemo1.DTO.File.FileNameUpdateRequest;
import com.example.mbsedemo1.Entity.File;
import com.example.mbsedemo1.Service.FileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@RestController
@Validated
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/byFolder/{folderId}")
    public List<File> getFilesByFolder(@PathVariable int folderId) {
        return fileService.getFilesByFolderId(folderId);
    }

    @GetMapping("/{fileId}")
    public File getFileById(@PathVariable int fileId) {
        return fileService.getFileById(fileId);
    }

    @GetMapping("/search")
    public List<File> searchFiles(@RequestParam String query) {
        return fileService.searchFiles(query);
    }

    @PatchMapping("/{fileId}/rename")
    public ResponseEntity<String> renameFile(@PathVariable Integer fileId, @RequestBody FileNameUpdateRequest request) {
        fileService.renameFile(fileId, request.getNewName());
        return ResponseEntity.ok("File renamed successfully.");
    }

    @PatchMapping("/{fileId}/updateContent")
    public ResponseEntity<String> updateFileContent(@PathVariable Integer fileId, @RequestBody FileContentUpdateRequest request) {
        fileService.updateFileContent(fileId, request.getNewContent());
        return ResponseEntity.ok("File content updated successfully.");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createFile(@RequestBody @Valid FileCreationRequest request) {
        File file = new File();
        file.setFolderId(request.getFolderId());
        file.setContent(request.getContent());
        file.setName(request.getName());
        // 假设你在File类中添加了其他必要的属性设置，比如uploadTime等
        fileService.createFile(file);

        return new ResponseEntity<>("File '" + request.getName() + "' created successfully", HttpStatus.CREATED);
    }
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("folderId") Integer folderId) {
        if (file.isEmpty()) {
            return "The file is empty";
        }

        // 限制文件类型为txt
        if (!file.getContentType().equals("text/plain")) {
            return "Only txt files are allowed";
        }

        try {
            byte[] bytes = file.getBytes();
            String content = new String(bytes);

            // 创建File对象并保存到数据库
            File fileToSave = new File();
            fileToSave.setName(file.getOriginalFilename());
            fileToSave.setSize(file.getSize());
            fileToSave.setUploadTime(new Timestamp(System.currentTimeMillis()));
            fileToSave.setFolderId(folderId);
            fileToSave.setContent(content);

            fileService.save(fileToSave); // 调用Service层方法保存File对象

            return "File uploaded successfully: " + file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed: " + e.getMessage();
        }
    }


// 其他与文件相关的操作...
}
