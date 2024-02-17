package com.example.app.service;


import com.example.app.domain.dao.ReplyDAO;
import com.example.app.domain.vo.ReplyVO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyDAO replyDAO;

    //    댓글목록
    public List<ReplyVO> getList( int page, int rowCount, Long boardId) {
        return replyDAO.findAllByBoardId(page, rowCount, boardId);
    }

    //    댓글추가
    public void register(ReplyVO replyVO) {
        replyDAO.save(replyVO);
    }

    //    댓글수정
    public void modify(ReplyVO replyVO) {
        replyDAO.setReply(replyVO);
    }

    //    댓글삭제
    public void remove(Long replyId) {
        replyDAO.remove(replyId);
    }
}
