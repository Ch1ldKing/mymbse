package com.example.mbsedemo1.Mapper;

import com.example.mbsedemo1.Entity.Project;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ProjectMapper {

    @Insert("INSERT INTO projects (name, created_at) VALUES (#{name}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Project project);

    @Select("SELECT * FROM projects WHERE id = #{id}")
    Project findById(int id);

    @Select("SELECT * FROM projects")
    List<Project> findAll();

    @Update("UPDATE projects SET name = #{name} WHERE id = #{id}")
    void update(Project project);

    @Delete("DELETE FROM projects WHERE id = #{id}")
    void delete(int id);
}
