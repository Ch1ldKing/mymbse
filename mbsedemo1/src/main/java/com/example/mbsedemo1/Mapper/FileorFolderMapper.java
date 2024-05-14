package com.example.mbsedemo1.Mapper;

import com.example.mbsedemo1.Entity.File;
import com.example.mbsedemo1.Entity.FileorFolder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileorFolderMapper {
    @Select("SELECT * FROM fileorfolders WHERE project_id = #{projectId} AND parent_id = #{parentId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "isFile", column = "is_file"),
            @Result(property = "children", column = "id", javaType = List.class,
                    many = @Many(select = "selectByParentId"))
    })
    List<FileorFolder> findByProjectIdAndParentId(@Param("projectId") Integer projectId,@Param("parentId") Integer parentId);

    @Select("SELECT * FROM fileorfolders WHERE project_id = #{projectId} AND parent_id IS NULL")
    List<FileorFolder> findUpFolder(Integer projectId);

    @Select("SELECT * FROM fileorfolders WHERE parent_id = #{parentId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "parentId", column = "parentId"),
            @Result(property = "isFile", column = "is_file"),
            @Result(property = "children", column = "id", javaType = List.class,
                    many = @Many(select = "findByParentId"))
    })
    List<FileorFolder> findByParentId(@Param("parentId") Integer parentId);

    @Select("SELECT * FROM fileorfolders WHERE id = #{id}")
    FileorFolder findById(int id);

    @Select("SELECT * FROM fileorfolders WHERE project_id = #{projectId}")
    List<FileorFolder> findByProjectId(Integer projectId);

    @Select("SELECT * FROM fileorfolders")
    List<FileorFolder> getAllFileorFolder();

    @Insert("INSERT INTO fileorfolders (name, size, upload_time, content, is_file, project_id, parent_id) VALUES (#{name}, #{size}, #{uploadTime}, #{content}, #{isFile}, #{projectId}, #{parentId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(FileorFolder fileorFolder);

    @Update("UPDATE fileorfolders SET name = #{name}, size = #{size}, upload_time = #{uploadTime}, content = #{content} WHERE id = #{id}")
    void update(FileorFolder fileorFolder);

    @Delete("DELETE FROM fileorfolders WHERE id = #{id}")
    void delete(int id);

    @Select("SELECT * FROM fileorfolders WHERE to_tsvector('english', content) @@ plainto_tsquery('english',#{searchQuery})")
    List<FileorFolder> searchByContent(String searchQuery);

}
