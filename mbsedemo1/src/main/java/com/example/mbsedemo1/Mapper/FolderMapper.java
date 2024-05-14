package com.example.mbsedemo1.Mapper;

import com.example.mbsedemo1.Entity.Folder;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface FolderMapper {

    @Insert("INSERT INTO folders (project_id, name, created_at) VALUES (#{projectId}, #{name}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Folder folder);

    @Select("SELECT * FROM folders WHERE id = #{id}")
    Folder findById(int id);

    @Select("SELECT * FROM folders WHERE project_id = #{projectId}")
    List<Folder> findByProjectId(int projectId);

    @Update("UPDATE folders SET name = #{name} WHERE id = #{id}")
    void update(Folder folder);

    @Delete("DELETE FROM folders WHERE id = #{id}")
    void delete(int id);


}
