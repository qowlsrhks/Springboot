package com.example.app.service;

import com.example.app.domain.dao.FileDAO;
import com.example.app.domain.vo.FileVO;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileDAO fileDAO;
    //    파일추가
    public void register(FileVO fileVO) {
        fileDAO.save(fileVO);
    }

    //    파일삭제
    public void remove(Long boardId) {
        fileDAO.delete(boardId);
    }

    //    파일목록
    public List<FileVO> getList(Long boardId) {
        return fileDAO.findAllByBoardId(boardId);
    }

//    파일 업로드
@PostMapping("upload")
public List<FileVO> upload(List<MultipartFile> multipartFiles) throws IOException {
    String rootPath = "C:/upload";
    String todayPath = creatDirectoryToday();
    File uploadPath = new File(rootPath, todayPath);
    List<FileVO> files = new ArrayList<>();
    if (!uploadPath.exists()) {
        uploadPath.mkdirs();
    }

    for (MultipartFile multipartFile : multipartFiles) {
        FileVO fileVo = new FileVO();
        String uuid = UUID.randomUUID().toString();
        String fileName = multipartFile.getOriginalFilename();
        String fileSize = String.format("%.2f", multipartFile.getSize() / 1024.0);
        boolean isImage = multipartFile.getContentType().startsWith("image");

        fileVo.setFileUuid(uuid);
        fileVo.setFileName(fileName);
        fileVo.setFileUploadPath(todayPath);
        fileVo.setFileSize(fileSize);
        fileVo.setFileIsImage(isImage);

        File save = new File(uploadPath, uuid + "_" + fileName);
        multipartFile.transferTo(save);

        if (isImage) {
            FileOutputStream out = new FileOutputStream(new File(uploadPath, "t_" + uuid + "_" + fileName));
            Thumbnailator.createThumbnail(multipartFile.getInputStream(), out, 100, 100);
            out.close();
        }
        files.add(fileVo);
    }
    return files;
}

//    당일 날짜 업로드 경로
private String creatDirectoryToday(){
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
}
}
