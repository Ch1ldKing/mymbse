package com.example.mbsedemo1.Mapper;

import com.example.mbsedemo1.Entity.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import org.apache.ibatis.annotations.*;


@Mapper
public interface FileMapper {
    // 插入新文件
    @Insert("INSERT INTO files (name, size, upload_time, content, folder_id) VALUES (#{name}, #{size}, #{uploadTime}, #{content}, #{folderId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(File file);

    // 查询所有文件
    @Select("SELECT * FROM files")
    List<File> findAll();

    // 通过ID查询单个文件
    @Select("SELECT * FROM files WHERE id = #{id}")
    File findById(int id);

    @Select("SELECT * FROM files WHERE folder_id = #{folderId}")
    List<File> findByFolderId(int folderId);

    // 更新文件记录
    @Update("UPDATE files SET name = #{name}, size = #{size}, upload_time = #{uploadTime}, content = #{content}, search_vector = to_tsvector('english', #{content}) WHERE id = #{id}")
    void update(File file);

    // 删除所有文件记录
    @Delete("DELETE FROM files")
    void deleteAll();
    // 删除文件记录
    @Delete("DELETE FROM files WHERE id = #{id}")
    void delete(int id);

    // 全文搜索查询
    @Select("SELECT * FROM files WHERE to_tsvector('english', content) @@ plainto_tsquery('english',#{searchQuery})")
    List<File> searchByContent(String searchQuery);
}
