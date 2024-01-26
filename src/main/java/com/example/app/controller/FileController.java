package com.example.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/files/*")
@Slf4j
public class FileController {
    @PostMapping("upload")
    public void upload(List<MultipartFile> upload) throws IOException {
        String rootPath = "C:/upload";
        File uploadPath = new File(rootPath, creatDirectoryToday());
        if(!uploadPath.exists()){uploadPath.mkdirs();}

        for (MultipartFile multipartFile : upload) {
            File save = new File(uploadPath, multipartFile.getOriginalFilename());
            multipartFile.transferTo(save);
        }
    }

    private String creatDirectoryToday(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
