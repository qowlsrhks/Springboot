package com.example.app.dao;

import com.example.app.domain.dao.BoardDAO;
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
public class BoardDaoTests {

    @Autowired
    BoardDAO boardDAO;

    @Test
    public void findByIdTest() {
        assertThat(boardDAO.findById(2L).getBoardTitle()).isEqualTo("테스트 제목1");
    }

    @Test
    public void findAllTest() {
        Search search = new Search();
        Criteria criteria = new Criteria();
        search.setType("c");
        search.setKeyword("재밌어");
        criteria.setPage(1);
        criteria.create(boardDAO.findCountAll(search));
        assertThat(boardDAO.findAll(search, criteria).size()).isEqualTo(2);
    }

    @Test
    public void saveTest() {
        BoardVO boardVO = BoardVO.builder()
                .boardTitle("새로운 제목")
                .boardContent("새로운 내용")
                .boardWriter("Deruco")
                .build();
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
