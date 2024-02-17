package com.example.app.service;


import com.example.app.domain.vo.ReplyVO;
import com.example.app.mapper.ReplyMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyMapper replyMapper;

    //    댓글목록
    public List<ReplyVO> findAllByBoardId( int page, int rowCount, Long boardId) {
        return replyMapper.selectAllBoardId(page, rowCount, boardId);
    }

    //    댓글추가
    public void save(ReplyVO replyVO) {
        replyMapper.insert(replyVO);
    }

    //    댓글수정
    public void setReply(ReplyVO replyVO) {
        replyMapper.update(replyVO);
    }

    //    댓글삭제
    public void delete(Long replyId) {
        replyMapper.delete(replyId);
    }
}
