package com.example.app.mapper;

import com.example.app.domain.dto.Criteria;
import com.example.app.domain.dto.Search;
import com.example.app.domain.vo.BoardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface BoardMapper {
//    게시글 조회
    public BoardVO select(Long boardId);

//    게시글 목록
    public List<BoardVO> selectAll(@Param("search") Search search, @Param("cri") Criteria criteria);

    //    게시글 추가
    public void insert(BoardVO boardVO);

    //    게시글 삭제
    public void delete(Long boardId);

    //    게시글 수정
    public void update(BoardVO boardVO);

    //    전체 게시글 조회
    public int selectCountAll();
}
