package com.example.mbsedemo1.Entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class FileorFolder {
    private Integer id;
    private String name;
    private Long size;
    private Timestamp uploadTime;
    private String content;
    private boolean isFile;
    private Integer projectId;
    private Integer parentId;

    private List<FileorFolder> children = new ArrayList<>();

    public void setChildren(List<FileorFolder> children) {
        this.children = children;
    }
}
