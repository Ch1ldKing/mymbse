package com.example.mbsedemo1.Entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class File {
    private Integer id;
    private String name;
    private Long size;
    private Timestamp uploadTime;
    private String content;
    private Integer folderId; // 外键，指向 Folder

    // 构造函数

    // toString 方法，可选，用于打印对象信息
    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", uploadTime=" + uploadTime +
                ", content='" + content + '\'' +
                '}';
    }
}
