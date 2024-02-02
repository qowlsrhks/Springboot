package com.example.app.domain.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class FileVO {
    private Long fileId;
    private String fileName;
    private String fileUploadPath;
    private String fileUuid;
    private String fileSize;
    private boolean fileIsImage;
    private Long BoardId;


}
