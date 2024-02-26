package com.example.app.mapper;

import com.example.app.domain.vo.ReplyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {
    //    댓글목록
    public List<ReplyVO> selectAllBoardId(@Param("page") int page,@Param("rowCount") int rowCount,@Param("boardId") Long boardId,@Param("types") String[] type, @Param("keyword") String keyword);

    //    다음페이지의 게시글 개수
    public int selectCountOfNextPage(@Param("page") int page, @Param("rowCount") int rowCount, @Param("boardId") Long boardId, @Param("types") String[] type, @Param("keyword") String keyword);


    //    댓글추가
    public void insert(ReplyVO replyVO);

    //    댓글수정
    public void update(ReplyVO replyVO);

    //    댓글삭제
    public void delete(Long replyId);


}
