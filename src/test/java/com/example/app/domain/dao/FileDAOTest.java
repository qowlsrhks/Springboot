package com.example.app.domain.dao;

import com.example.app.domain.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class FileDAOTest {

    @Autowired
    private FileDAO fileDAO;
    @Test
    void saveTest() {
        FileVO fileVO = new FileVO();
        fileVO.setBoardId(2068L);
        fileVO.setFileName("테스트 파일2.png");
        fileVO.setFileSize("0.5");
        fileVO.setFileUploadPath("2024/02/02");
        fileVO.setFileUuid(UUID.randomUUID().toString());
        fileVO.setFileIsImage(true);
        fileDAO.save(fileVO);
    }

    @Test
    void deleteTest() {
        fileDAO.delete(2068L);
        assertThat(fileDAO.findAllByBoardId(2068L).size()).isEqualTo(0);

    }

    @Test
    void findAllByBoardIdTest() {
        assertThat(fileDAO.findAllByBoardId(2068L).size()).isEqualTo(2);
    }
}