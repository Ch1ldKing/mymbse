package com.example.mbsedemo1.Service;

import com.example.mbsedemo1.Entity.File;
import com.example.mbsedemo1.Entity.Folder;
import com.example.mbsedemo1.Mapper.FileMapper;
import com.example.mbsedemo1.Mapper.FolderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FolderService {

    private final FolderMapper folderMapper;


    @Autowired
    public FolderService(FolderMapper folderMapper, FileMapper fileMapper) {
        this.folderMapper = folderMapper;
    }

    public void createFolder(Folder folder) {
        folderMapper.insert(folder);
    }

    public Folder getFolderById(int id) {
        return folderMapper.findById(id);
    }

    public List<Folder> getFoldersByProjectId(int projectId) {
        return folderMapper.findByProjectId(projectId);
    }



    public void updateFolder(Folder folder) {
        folderMapper.update(folder);
    }

    public void deleteFolder(int id) {
        folderMapper.delete(id);
    }
}
