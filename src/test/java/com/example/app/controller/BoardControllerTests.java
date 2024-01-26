package com.example.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.RedirectView;

@SpringBootTest
@Slf4j
public class BoardControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach //모든 단위 테스트가 실행되기 전에 이 메소드를 실행한다
    public void setUp() {                       //웹 브라우저를 여는 것과 동일
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void showListTest() throws Exception {                                //.andReturn() 주소창에 쓰고 엔터를 치는 것과 동일
        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list")
                        .param("page","1")
                        .param("type","twc")
                        .param("keyword","테스트 제목1")
                ).andReturn().getModelAndView().getModelMap().toString());
        //model에 담겨져있는것을 toString으로 출력
    }

    @Test
    public void getBoardTest() throws Exception {
        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/modify")
                .param("boardId", "3")).andReturn().getModelAndView().getModelMap().toString());

    }

    @Test
    public void writeTest() throws Exception {
        log.info(mockMvc.perform(MockMvcRequestBuilders.post("/board/write")
                .param("boardTitle", "새로운 제목")
                .param("boardContent", "새로운 내용")
                .param("boardWriter", "Deruco")
        ).andReturn().getFlashMap().toString());
    }

    @Test
    public void removeTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/board/remove")
                .param("boardId", "22"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
    @Test
    public void modifyTest() throws Exception {
        log.info(mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
                .param("boardId", "4")
                .param("boardTitle", "두번째 바뀌는 제목")
                .param("boardContent", "수정된 내용")
        ).andReturn().getModelAndView().getModel().toString());
    }


}
