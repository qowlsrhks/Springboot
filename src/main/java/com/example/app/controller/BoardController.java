package com.example.app.controller;

import com.example.app.domain.dto.BoardDTO;
import com.example.app.domain.dto.Criteria;
import com.example.app.domain.dto.Search;
import com.example.app.domain.vo.BoardVO;
import com.example.app.domain.vo.FileVO;
import com.example.app.service.BoardService;
import com.example.app.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final FileService fileService;

    //   게시글 목록
    @GetMapping("list")
    public void showList(Search search, Criteria criteria, Model model) {
        model.addAttribute("boards", boardService.getList(search, criteria));
    }

//    게시글 조회

    @GetMapping(value = {"read","modify"})
    public void getBoard(Search search, Criteria criteria, Long boardId, Model model) {
        model.addAttribute(boardService.getBoard(boardId));
        model.addAttribute("files", fileService.getList(boardId));

    }

    @GetMapping("write")
    public void write(Search search, Criteria criteria, Model model) {
        model.addAttribute(new BoardDTO());
    }

    //    게시글 작성
    /*
     * forward 방식은 처음 요청한 경로가(write) 마지막까지 남아있고,
     * redirect 방식은 마지막에 요청한 경로가 남아있다
     * forward 방식은 바로 .html로 간다
     * redirect 방식은 컨트롤러로 간다
     * */
    @PostMapping("write")
    public RedirectView write(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
//        boardDTO.getFiles().stream().map(FileVO::getFileName).forEach(log::info);
        boardService.write(boardDTO);
//        자동으로 쿼리 스트링으로 작성되며, 다음 컨트롤러에 데이터를 전달할 때 사용한다
//        redirectAttributes.addAttribute("boardId", boardVO.getBoardId());
//        Session에 있는 flash영역을 사용하여 request객체가 초기화 된 후 기존 정보들을 다시 담아줌으로써 화면에 데이터를 전달할 수있다.
        redirectAttributes.addFlashAttribute("boardId", boardDTO.getBoardId());
        return new RedirectView("/board/list");
    }

    //    게시글 삭제
    @GetMapping("remove")
    public RedirectView remove(Long boardId) {
        boardService.remove(boardId);
        return new RedirectView("/board/list");
    }
    
//    게시글 수정

    @PostMapping("modify")
    public RedirectView modify(Criteria criteria,Search search, BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        boardService.modify(boardDTO);
        redirectAttributes.addAttribute("boardId", boardDTO.getBoardId());
        redirectAttributes.addFlashAttribute(criteria);
        redirectAttributes.addFlashAttribute(search);
        return new RedirectView("/board/read");
    }
}
