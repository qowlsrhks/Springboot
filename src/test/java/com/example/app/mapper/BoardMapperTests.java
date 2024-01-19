package com.example.app.mapper;

import com.example.app.domain.dto.Criteria;
import com.example.app.domain.dto.Search;
import com.example.app.domain.vo.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class BoardMapperTests {

    @Autowired
    BoardMapper boardMapper;

    @Test
    public void selectTest() {
        assertThat(boardMapper.select(4L).getBoardTitle()).isEqualTo("테스트제목4");
    }

    @Test
    public void selectAllTest() {
        Search search = new Search();
        Criteria criteria = new Criteria();

        search.setTypes("t");
        search.setKeyword("Spring");

        criteria.setPage(1);
        criteria.create(boardMapper.selectCountAll());
        assertThat(boardMapper.selectAll(search, criteria).size()).isEqualTo(2);
    }

    @Test
    public void insertTest() {
        BoardVO boardVO = new BoardVO();
        boardVO.setBoardTitle("테스트제목5");
        boardVO.setBoardWriter("테스트5");
        boardVO.setBoardContent("테스트내용5");
        boardMapper.insert(boardVO);

        assertThat(boardVO.getBoardId()).isEqualTo(5L);
    }

    @Test
    public void deleteTest() {
        boardMapper.delete(5L);
    }

    @Test
    public void updateTest() {
        BoardVO boardVO = boardMapper.select(3L);
        boardVO.setBoardTitle("바뀐 제목");
        boardVO.setBoardContent("바뀐 내용");
        boardMapper.update(boardVO);
    }
}
