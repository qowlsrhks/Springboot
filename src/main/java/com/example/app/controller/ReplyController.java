package com.example.app.controller;


import com.example.app.domain.vo.ReplyVO;
import com.example.app.service.ReplyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

//@Controller return 값에 ViewResolver 객체가 관여한다
@RestController //reuturn 값을 JSON혹은 해당 값 그대로 화면에 전달한다
@RequestMapping("/replies/*")
public class ReplyController {


    //    test
//    @GetMapping("test")
//    public String test() {
//        return "Deruco";
//    }

    //    test2
//    @GetMapping("test2")
//    public List<String> test2() {
//        return Arrays.asList("ㄱ", "ㄴ", "ㄷ");
//    }

    private ReplyService replyService;

    //    댓글목록
    @GetMapping("list/{boardId}/{page}/{rowCount}")
    public List<ReplyVO> getList(@PathVariable Long boardId,@PathVariable int page,@PathVariable int rowCount) {
        return replyService.getList(page, rowCount, boardId);
    }
//    댓글작성
//    댓글수정
//    댓글삭제
}
