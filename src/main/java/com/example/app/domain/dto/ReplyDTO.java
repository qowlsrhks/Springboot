package com.example.app.domain.dto;

import com.example.app.domain.vo.ReplyVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
public class ReplyDTO {
    private List<ReplyVO> replies;
    private int countOfNextPage;

    public ReplyDTO(List<ReplyVO> ReplyVOs,int countOfNextPage) {
        this.replies = ReplyVOs;
        this.countOfNextPage = countOfNextPage;

    }
}
