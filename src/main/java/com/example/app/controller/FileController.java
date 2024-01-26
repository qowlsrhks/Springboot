package com.example.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files/*")
@Slf4j
public class FileController {
    @PostMapping("upload")
    public void upload(List<MultipartFile> upload) {
        String rootPath = "C:/upload";
    }
}
