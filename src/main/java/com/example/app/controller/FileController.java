package com.example.app.controller;

import com.example.app.domain.vo.FileVO;
import com.example.app.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/files/*")
@Slf4j
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("upload")
    public List<FileVO> upload(List<MultipartFile> multipartFiles) throws IOException {
        return fileService.upload(multipartFiles);
    }


    @GetMapping("display")
    public byte[] display(String filePath) throws IOException {
        File file = new File("C:/upload", filePath);
        return FileCopyUtils.copyToByteArray(file);
    }

    @GetMapping("download")
    public ResponseEntity<Resource> download(String filePath) throws UnsupportedEncodingException {
        Resource resource = new FileSystemResource("C:/upload/" + filePath);
        HttpHeaders header = new HttpHeaders();
        String name = resource.getFilename();
        header.add("Content-Disposition", "attachment;filename=" + new String(name.substring(name.indexOf("_") + 1).getBytes("UTF-8"), "ISO-8859-1"));
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }
}


