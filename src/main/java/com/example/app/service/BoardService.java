package com.example.app.service;

import com.example.app.domain.dao.BoardDAO;
import com.example.app.domain.dto.Criteria;
import com.example.app.domain.dto.Search;
import com.example.app.domain.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardDAO boardDAO;

    //    게시글 조회
    public BoardVO getBoard(Long boardId) {
        return boardDAO.findById(boardId);
    }

    //    게시글 목록
    public List<BoardVO> getList(Search search,Criteria criteria) {
        criteria.create(getTotal());
        return boardDAO.findAll(search, criteria);
    }

    //    게시글 추가
    public void write(BoardVO boardVo) {
        boardDAO.save(boardVo);
    }

    //    게시글 삭제
    public void remove(Long boardId) {
        boardDAO.delete(boardId);
    }

    //    게시글 수정
    public void modify(BoardVO boardVO) {
        boardDAO.setBoard(boardVO);
    }

    //    전체 게시글 조회
    public int getTotal() {
        return boardDAO.findCountAll();
    }

}
