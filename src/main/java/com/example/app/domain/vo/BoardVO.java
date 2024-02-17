package com.example.app.domain.vo;

import com.example.app.domain.dto.BoardDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
public class BoardVO {
    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private String boardRegisterDate;
    private String boardUpdateDate;

    public BoardDTO toDTO() {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardId(boardId);
        boardDTO.setBoardTitle(boardTitle);
        boardDTO.setBoardContent(boardContent);
        boardDTO.setBoardRegisterDate(boardRegisterDate);
        boardDTO.setBoardUpdateDate(boardUpdateDate);
        return boardDTO;
    }

}
