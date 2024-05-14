package com.example.mbsedemo1.Service;

import com.example.mbsedemo1.Entity.FileorFolder;
import com.example.mbsedemo1.Mapper.FileorFolderMapper;
import com.example.mbsedemo1.Mapper.FolderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectStructureService {

    @Autowired
    private FileorFolderMapper fileorfolderMapper;

    public List<FileorFolder> getProjectStructure(Integer projectId) {
        // 获取所有顶层节点，即那些父ID为null的节点
        List<FileorFolder> topNodes = fileorfolderMapper.findUpFolder(projectId);
        topNodes.forEach(this::buildStructure);
        return topNodes;
    }

    private void buildStructure(FileorFolder node) {
        // 递归加载子节点
        List<FileorFolder> children = fileorfolderMapper.findByParentId(node.getId());
        children.forEach(this::buildStructure);
        node.setChildren(children);
    }
}
