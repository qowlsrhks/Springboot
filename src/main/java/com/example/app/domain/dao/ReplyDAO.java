package com.example.app.domain.dao;

import com.example.app.domain.vo.ReplyVO;
import com.example.app.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReplyDAO {

    private final ReplyMapper replyMapper;

    //    댓글목록
    public List<ReplyVO> findAllByBoardId(int page, int rowCount, Long boardId) {
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
    public void remove(Long replyId) {
        replyMapper.delete(replyId);
    }

}
