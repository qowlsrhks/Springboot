package com.example.app.dao;

import com.example.app.domain.dao.ReplyDAO;
import com.example.app.domain.vo.ReplyVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class replyDaoTest {
    @Autowired
    private ReplyDAO replyDAO;

//    댓글목록
    @Test
    public void selectAllByBoardIdTest() {
//        assertThat(replyDAO.selectAllBoardId(2, 10, 2101L)).hasSize(2);
        replyDAO.findAllByBoardId(1, 10, 2101L).stream().map(ReplyVO::toString).forEach(log::info);

    }

    //    댓글 작성
    @Test
    public void insertTest() {
        ReplyVO replyVO = new ReplyVO();
        replyVO.setReplyContent("테스트 내용4");
        replyVO.setReplyWriter("테스트 작성자4");
        replyVO.setBoardId(2101L);
        replyDAO.save(replyVO);
    }

    //    댓글 수정
    @Test
    public void updateTest() {
        ReplyVO replyVO = replyDAO.findAllByBoardId(1,10,2101L).get(0);
        replyVO.setReplyContent("수정된 내용2");
        replyDAO.setReply(replyVO);
    }

//    댓글 삭제

    @Test
    public void delete() {
        replyDAO.remove(2L);
    }

}
