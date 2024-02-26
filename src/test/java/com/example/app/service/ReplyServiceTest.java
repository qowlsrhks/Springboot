package com.example.app.service;

import com.example.app.domain.vo.ReplyVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class ReplyServiceTest {
    @Autowired
    private ReplyService replyService;

//    댓글목로
    @Test
    public void selectAllByBoardIdTest() {
//        assertThat(replyService.selectAllBoardId(2, 10, 2101L)).hasSize(2);
        replyService.getList(1, 10, 2101L,null,null).stream().map(ReplyVO::toString).forEach(log::info);

    }

    //    댓글 작성
    @Test
    public void insertTest() {
        ReplyVO replyVO = new ReplyVO();
        replyVO.setReplyContent("테스트 내용4");
        replyVO.setReplyWriter("테스트 작성자4");
        replyVO.setBoardId(2101L);
        replyService.register(replyVO);
    }

    //    댓글 수정
    @Test
    public void updateTest() {
        ReplyVO replyVO = replyService.getList(1,10,2101L,null,null).get(0);
        replyVO.setReplyContent("수정된 내용2");
        replyService.modify(replyVO);
    }

//    댓글 삭제

    @Test
    public void delete() {
        replyService.remove(2L);
    }

}
