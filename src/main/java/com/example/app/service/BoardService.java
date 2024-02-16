package com.example.app.service;

import com.example.app.domain.dao.BoardDAO;
import com.example.app.domain.dao.FileDAO;
import com.example.app.domain.dto.BoardDTO;
import com.example.app.domain.dto.Criteria;
import com.example.app.domain.dto.Search;
import com.example.app.domain.vo.BoardVO;
import com.example.app.domain.vo.FileVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardDAO boardDAO;
    private final FileDAO fileDAO;

    //    게시글 조회
    public BoardVO getBoard(Long boardId) {
        return boardDAO.findById(boardId);
    }

    //    게시글 목록
    public List<BoardVO> getList(Search search,Criteria criteria) {
        criteria.create(getTotal(search));
        return boardDAO.findAll(search, criteria);
    }

    //    게시글 추가
    @Transactional(rollbackFor = Exception.class)
    public void write(BoardDTO boardDTO) {
        BoardVO boardVO = boardDTO.toVO();
        boardDAO.save(boardVO);

        boardDTO.getFiles().forEach(file -> {
            file.setBoardId(boardVO.getBoardId());
            fileDAO.save(file);
        });
    }

    //    게시글 삭제
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long boardId) {
        List<FileVO> files = fileDAO.findAllByBoardId(boardId);
        if(files != null){
            fileDAO.delete(boardId);
        }
        boardDAO.delete(boardId);
    }

    //    게시글 수정
    @Transactional(rollbackFor = Exception.class)
    public void modify(BoardDTO boardDTO) {
        boardDAO.setBoard(boardDTO.toVO());

        if (boardDTO.getFiles() != null) {
            boardDTO.getFiles().forEach(file -> {
                file.setBoardId(boardDTO.getBoardId());
                fileDAO.save(file);
            });
        }

        if (boardDTO.getDeletedFiles() != null) {
            boardDTO.getDeletedFiles().stream().map(FileVO::getFileId).forEach(fileDAO::deleteById);
        }
    }

    //    전체 게시글 조회
    public int getTotal(Search search) {
        return boardDAO.findCountAll(search);
    }

}
