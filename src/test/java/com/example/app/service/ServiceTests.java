package com.example.app.service;

import com.example.app.domain.dto.Criteria;
import com.example.app.domain.dto.Search;
import com.example.app.domain.vo.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class ServiceTests {

    @Autowired
    BoardService boardService;
    
    @Test
    public void getBoardTest() {
        assertThat(boardService.getBoard(2L).getBoardTitle()).isEqualTo("테스트 제목1");
    }

    @Test
    public void getListTest(){
        Search search = new Search();
        Criteria criteria = new Criteria();
        criteria.setPage(1);
        criteria.create(boardService.getTotal());
        assertThat(boardService.getList(search, criteria).size()).isEqualTo(10);
    }

    @Test
    public void writeTest() {
        BoardVO boardVO = new BoardVO();
        boardVO.setBoardTitle("테스트제목6");
        boardVO.setBoardWriter("테스트6");
        boardVO.setBoardContent("테스트내용6");
        boardService.write(boardVO);

        assertThat(boardVO.getBoardId()).isEqualTo(8L);
    }

    @Test
    public void removeTest() {
        boardService.remove(8L);
    }

    @Test
    public void modifyTest() {
        BoardVO boardVO = boardService.getBoard(3L);
        boardVO.setBoardTitle("바뀐 제목3");
        boardVO.setBoardContent("바뀐 내용3");
        boardService.modify(boardVO);
    }
}
