package com.example.app.domain.dto;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Criteria {
  private int page; //현재 페이지
  private int rowCount; //보이는 게시글 갯수
  private int pageCount; //한번에 보이는 페이지 수
  private int startPage; //첫 페이지
  private int endPage; //끝나는 페이지
  private int realEnd; //마지막 페이지
  private boolean prev, next;//넘기는 버튼
  private int total; //총 페이지 수

    public Criteria() {
        this.page = 1;
    }

    public void create(int total) {
        this.total = total;
        this.rowCount = 10;
        this.pageCount = 10;
        this.endPage = (int)Math.ceil(page / ((double)pageCount)) * pageCount;
        this. startPage = endPage - pageCount + 1;
        this.realEnd = (int)Math.ceil(total / (double)rowCount);

        if (realEnd < endPage) {
            endPage = realEnd == 0 ? 1 : realEnd;
        }

        this.prev = startPage > 1;
        this.next = endPage < realEnd;
    }
}
