package com.example.app.mapper;

import com.example.app.domain.vo.ReplyVO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class ReplyMapperTest {
    @Autowired
    private ReplyMapper replyMapper;

//    댓글 목록
    @Test
    public void selectAllByBoardIdTest() {
//        assertThat(replyMapper.selectAllBoardId(2, 10, 2101L)).hasSize(2);
        replyMapper.selectAllBoardId(2, 10, 2101L,null,null).stream().map(ReplyVO::toString).forEach(log::info);

    }

    @Test
    public void selectCountOfNextPage() {
        log.info(replyMapper.selectCountOfNextPage(1,10,2101L,null,null)+"건");
    }

//    댓글 작성
    @Test
    public void insertTest() {
        ReplyVO replyVO = new ReplyVO();
        replyVO.setReplyContent("테스트 내용3");
        replyVO.setReplyWriter("테스트 작성자3");
        replyVO.setBoardId(2101L);
        replyMapper.insert(replyVO);
    }

//    댓글 수정
    @Test
    public void updateTest() {
        ReplyVO replyVO = replyMapper.selectAllBoardId(1,10,2101L,null,null).get(0);
        replyVO.setReplyContent("수정된 내용1");
        replyMapper.update(replyVO);
    }

//    댓글 삭제

    @Test
    public void delete() {
        replyMapper.delete(1L);
    }

}
