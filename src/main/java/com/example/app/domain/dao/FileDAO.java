package com.example.app.domain.dao;

import com.example.app.domain.vo.FileVO;
import com.example.app.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileDAO {
    private final FileMapper fileMapper;
    //    파일추가
    public void save(FileVO fileVO) {
        fileMapper.insert(fileVO);
    }

    //    파일삭제
    public void delete(Long boardId) {
        fileMapper.delete(boardId);
    }

    //    파일목록
    public List<FileVO> findAllByBoardId(Long boardId) {
        return fileMapper.selectAllByBoardId(boardId);
    }

}
