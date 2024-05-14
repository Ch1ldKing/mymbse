package com.example.mbsedemo1.Service;

import org.springframework.stereotype.Service;
import com.example.mbsedemo1.Entity.FileorFolder;
import com.example.mbsedemo1.Mapper.FileorFolderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileorFolderService {

    private final FileorFolderMapper fileorFolderMapper;

    @Autowired
    public FileorFolderService(FileorFolderMapper fileorFolderMapper) {
        this.fileorFolderMapper = fileorFolderMapper;
    }

    public FileorFolder findById(int id) {
        return fileorFolderMapper.findById(id);
    }

    public void insert(FileorFolder fileorFolder) {
        fileorFolderMapper.insert(fileorFolder);
    }

    public void update(FileorFolder fileorFolder) {
        fileorFolderMapper.update(fileorFolder);
    }
    public void updateContentById(FileorFolder fileorFolder) {
        Integer id = fileorFolder.getId();
        String content = fileorFolder.getContent();
        FileorFolder fileorFolder1 = fileorFolderMapper.findById(id);
        fileorFolder1.setContent(content);
        fileorFolderMapper.update(fileorFolder1);
    }
    public void delete(int id) {
        fileorFolderMapper.delete(id);
    }

    public List<FileorFolder> findByProjectIdAndParentId(Integer projectId, Integer parentId) {
        return fileorFolderMapper.findByProjectIdAndParentId(projectId, parentId);
    }

    public List<FileorFolder> findByProjectId(Integer projectId) {
        return fileorFolderMapper.findByProjectId(projectId);
    }
public List<FileorFolder> getAllFileorFolder() {
        return fileorFolderMapper.getAllFileorFolder();
    }
    public List<FileorFolder> findUpFolder(Integer projectId) {
        return fileorFolderMapper.findUpFolder(projectId);
    }

    public List<FileorFolder> findByParentId(Integer parentId) {
        return fileorFolderMapper.findByParentId(parentId);
    }

    public List<FileorFolder> searchByContent(String searchQuery) {
        return fileorFolderMapper.searchByContent(searchQuery);
    }
}
