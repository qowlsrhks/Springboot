package com.example.app.domain.dto;

import com.example.app.domain.vo.BoardVO;
import com.example.app.domain.vo.FileVO;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class BoardDTO {
    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private String boardRegisterDate;
    private String boardUpdateDate;
    private List<FileVO> files;
    private List<FileVO> deletedFiles;

    public BoardVO toVO() {
        return BoardVO.builder()
                .boardId(boardId)
                .boardTitle(boardTitle)
                .boardWriter(boardWriter)
                .boardContent(boardContent)
                .boardRegisterDate(boardRegisterDate)
                .boardUpdateDate(boardUpdateDate)
                .build();
    }

}
