package com.example.app.dao;

import com.example.app.domain.dao.BoardDAO;
import com.example.app.domain.dto.Criteria;
import com.example.app.domain.vo.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class BoardDaoTests {

    @Autowired
    BoardDAO boardDAO;

    @Test
    public void findByIdTest() {
        assertThat(boardDAO.findById(2L).getBoardTitle()).isEqualTo("테스트 제목1");
    }

    @Test
    public void findAllTest() {
        Criteria criteria = new Criteria();
        criteria.setPage(1);
        criteria.create(boardDAO.findCountAll());
        assertThat(boardDAO.findAll(criteria).size()).isEqualTo(10);
    }

    @Test
    public void saveTest() {
        BoardVO boardVO = new BoardVO();
        boardVO.setBoardTitle("테스트제목6");
        boardVO.setBoardWriter("테스트6");
        boardVO.setBoardContent("테스트내용6");
        boardDAO.save(boardVO);

        assertThat(boardVO.getBoardId()).isEqualTo(6L);
    }

    @Test
    public void deleteTest() {
        boardDAO.delete(5L);
    }

    @Test
    public void setBoardTest() {
        BoardVO boardVO = boardDAO.findById(3L);
        boardVO.setBoardTitle("바뀐 제목2");
        boardVO.setBoardContent("바뀐 내용2");
        boardDAO.setBoard(boardVO);
    }

}
