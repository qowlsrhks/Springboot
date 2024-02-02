package com.example.app.controller;

import com.example.app.domain.vo.FileVO;
import com.example.app.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files/*")
@Slf4j
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("upload")
    public List<FileVO>upload(List<MultipartFile> multipartFiles) throws IOException {
        return fileService.upload(multipartFiles);
    }


    @GetMapping("display")
    public byte[] display(String filePath) throws IOException{
        File file = new File("C:/upload", filePath);
        return FileCopyUtils.copyToByteArray(file);
    }

}

