package com.example.app.controller;


import com.example.app.domain.vo.ReplyVO;
import com.example.app.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller return 값에 ViewResolver 객체가 관여한다
@RestController //reuturn 값을 JSON혹은 해당 값 그대로 화면에 전달한다
@RequestMapping("/replies/*")
@RequiredArgsConstructor
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

    private final ReplyService replyService;

    //    댓글목록
    @GetMapping("list/{boardId}/{page}/{rowCount}")
    public List<ReplyVO> getList(@PathVariable Long boardId, @PathVariable int page, @PathVariable int rowCount) {
        return replyService.getList(page, rowCount, boardId);
    }

    //    댓글작성
    @PostMapping("write")
    public void write(@RequestBody ReplyVO replyVO) {
        replyService.register(replyVO);
    }

    //    댓글수정
    @PostMapping("update")
    public void modify(@RequestBody ReplyVO replyVO) {
        replyService.modify(replyVO);
    }

    //    댓글삭제
    @DeleteMapping("{replyId}")
    public void remove(@PathVariable Long replyId) {
        replyService.remove(replyId);
    }
}
