package com.example.app.domain.dao;

import com.example.app.domain.dto.Criteria;
import com.example.app.domain.dto.Search;
import com.example.app.domain.vo.BoardVO;
import com.example.app.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardDAO {
    private final BoardMapper boardMapper;

    //    게시글 조회
    public BoardVO findById(Long boardId) {
        return boardMapper.select(boardId);
    }

    //    게시글 목록
    public List<BoardVO> findAll(Search search,Criteria criteria) {
        return boardMapper.selectAll(search,criteria);
    }

    //    게시글 추가
    public void save(BoardVO boardVo) {
        boardMapper.insert(boardVo);
    }

    //    게시글 삭제
    public void delete(Long boardId) {
        boardMapper.delete(boardId);
    }

    //    게시글 수정
    public void setBoard(BoardVO boardVO) {
        boardMapper.update(boardVO);
    }

    //    전체 게시글 조회
    public int findCountAll() {
        return boardMapper.selectCountAll();
    }


}
