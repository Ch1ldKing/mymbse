package com.example.mbsedemo1;

import com.example.mbsedemo1.Controller.ProjectController;
import com.example.mbsedemo1.Entity.File;
import com.example.mbsedemo1.Entity.FileorFolder;
import com.example.mbsedemo1.Mapper.FileMapper;
import com.example.mbsedemo1.Mapper.FileorFolderMapper;
import com.example.mbsedemo1.Mapper.ProjectMapper;
import com.example.mbsedemo1.Service.ProjectStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.util.List;

@SpringBootApplication
public class Mbsedemo1Application {

    public static void main(String[] args)
    {
        SpringApplication.run(Mbsedemo1Application.class, args);

    }
    @Bean
    public CommandLineRunner run() {
        return args -> {
            //测试代码
//            testGetProjectStructure();
//            testGetFolders();
        };
    }
    @Autowired
    private ProjectStructureService projectStructureService;

    @Autowired
    private FileorFolderMapper fileorFolderMapper;
    @Autowired
    private ProjectController projectController;
public void testGetFolders(){
    List<FileorFolder> lists = fileorFolderMapper.findUpFolder(1);
    for (FileorFolder fileorFolder : lists) {
        System.out.println(fileorFolder.getId());
    }
}

    public void testGetProjectStructure(){

        //测试代码
        System.out.println(projectController.getProjectStructure(1).getBody());


    }




}
