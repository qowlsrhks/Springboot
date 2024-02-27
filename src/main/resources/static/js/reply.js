/*###### 모듈화 ########*/
let replyService = (function (){
    function getList (callback){
        $.ajax({
            url: `/replies/list/${boardId}/${replyPage}/${rowCount}?type=${replyType}&keyword=${replyKeyword}`,
            dataType: "json",
            success: function (replyDTO){
                if(callback){
                    callback(replyDTO)
                }
            }
        });
    }

    function write(callback) {
        $.ajax({
            url:"/replies/write",
            type: "post",
            data:JSON.stringify( {replyWriter : $("input[name='replyWriter']").val(), replyContent: $("textarea[name='replyContent']").val(), boardId:boardId}),
            contentType: "application/json;charset=utf-8",
            success: function () {
                if (callback) {
                    callback();
                }
            }
        });
    }

    function modify(callback) {
        $.ajax({
            url: "/replies/modify",
            type: "post",
            data: JSON.stringify({replyContent: $("textarea.reply-content-modify").val(),replyId: $("textarea.reply-content-modify").closest("li").data("reply-id")}),
            contentType: "application/json;charset=utf-8",
            success: function (){
                if (callback) {
                    callback();
                }
            }
        });
    }

    function remove(replyId, callback){
        $.ajax({
            url: `/replies/${replyId}`,
            type: "delete",
            success: function () {
                if (callback) {
                    callback(replyId);
                }
            },
        });
    }

    return{getList: getList, write: write, modify: modify, remove: remove};
})();

replyService.getList(showList);


/*###### Event ########*/

    let check = false
// 엔터를 눌렀을 때 검색 버튼이 눌리게하기
$("input[name=keyword]").on("keydown", function (e) {
    if(e.keyCode == 13){
        e.preventDefault();
        replyPage = 1;
        $("a.search").trigger("click");
    }
});


// 검색기능
$("a.search").on("click", function () {
    replyType = $("select[name='type']").val();
    replyKeyword = $("input[name='keyword']").val();

    if (!replyType || !replyKeyword) {
        return;
    }
    replyPage = 1;
    $("ul.replies").html("");
    replyService.getList(showList);
});

$("a.more-replies").on("click", function () {
    replyPage++;
    replyService.getList(showList);

});


// 삭제버튼 눌렀을 때
$("ul.replies").on("click", "a.remove", function () {
  let i = $("a.remove").index(this);
  let replyId = $(this).closest("li").data("reply-id");
    replyService.remove(replyId, deleteReply);
});

function deleteReply(replyId) {
    let count = 0;
    $("li").remove(`li[data-reply-id=${replyId}]`);
    count++;
    if(count == 10 * replyPage){
        location.reload();
    }
}

$("ul.replies").on("click", "a.modify", function () {
    check = false;
    let i = $(this).parent("div").attr("id");
    replyService.modify(update);
});

// 수정완료 눌렀을 때 i번째있는 댓글만 수정되게 보이게함
function update(i) {
    const $p = $("textarea.reply-content-modify").closest("div").find($("p.reply-content"));
    $p.text($("textarea.reply-content-modify").val());
    $("a").remove("a.modify");
    $("a").remove("a.modify-cancel");
    $("textarea").remove("textarea.reply-content-modify");
    $p.show();
    $p.closest("li").find("a.modify-ready").show();
    $p.closest("li").find("a.remove").show();
}

    $("ul.replies").on("click", "a.modify-ready", function () {
    if(check){return;}
    let i = $("a.modify-ready").index(this);
    $(this).hide();
    $("a.remove").eq(i).hide();
    $(this).parent("div").append(`<a class="modify" style="cursor: pointer; margin: 5px">수정완료</a>`);
    $(this).parent("div").append(`<a class="modify-cancel" style="cursor: pointer">취소</a>`);
    $("p.reply-content").eq(i).hide();
    $("p.reply-content").eq(i).closest("div").append(`<textarea name="replyContent" style="resize: none; width: 100%" cols="120" class="reply-content-modify">${$("p.reply-content").eq(i).text()}</textarea>`);
    check = true;
});


$("ul.replies").on("click", "a.modify-cancel",function () {
    check = false;
    let i = $(this).closest("div").attr("id");
    $("a").remove("a.modify");
    $("a").remove("a.modify-cancel");
    $("textarea").remove("textarea.reply-content-modify");
    $("p.reply-content").eq(i).show();
    $("a.modify-ready").eq(i).show();
    $("a.remove").eq(i).show();
});

// 등록하기 누르면 작성자 댓글 쓰는 창 보이고 등록버튼 없어지게 하기
$("a.register").on("click", function () {
    $("div.register-form").show();
    $(this).hide();
});

// 취소버튼 누르면 내용 작성자 삭제하고 창닫기
$("a.cancel").on("click", function () {
    $("div.register-form").hide();
    $("a.register").show();
    $("input[name='replyWriter']").val("");
    $("textarea[name='replyContent']").val("");
});


/*###### DOM ########*/
// 등록하기 누르면 등록
$("a.finish").on("click", function () {
    replyService.write(register);
});

// 댓글 등록 창
function register() {
    replyPage = 1;
    $("ul.replies").html("");
    $("a.cancel").trigger("click");
    replyService.getList(showList);
}


/*###### DOM ########*/
// 댓글목록
function showList(replyDTO){
    let replies = replyDTO.replies;
    let countOfNextPage = replyDTO.countOfNextPage;
    let text = "";
    if(replies.length == 0 && replyPage == 1){
        text=`
            <p>댓글이 없습니다!</p>
        `
        $("a.more-replies").hide();
    }
    if(countOfNextPage == 0){
        $("a.more-replies").hide();
    }
    else{
        $("a.more-replies").show();
    }
    replies.forEach((reply, i) => {
        text += `
          <li style="display: block" data-reply-id="${reply.replyId}" >
              <div style="display: flex; justify-content: space-between">
                    <strong style="display: block">${reply.replyWriter}</strong>
                  <div id="${i}">
                      <a class="modify-ready" style="cursor: pointer; margin: 5px">수정</a>
                      <a class="remove" style="cursor: pointer">삭제</a>
                  </div>
              </div>
              <div style="display: flex; justify-content: space-between">
                <div>
                    <p class="reply-content">${reply.replyContent}</p>
                </div>
                <p style="text-align: right">
                    <strong class="date">
                        ${elapsedTime(reply.replyRegisterDate)}
                    </strong>
                </p>
              </div>
              <div class="line"></div>
          </li>
      `;
    });
    $("ul.replies").append(text);
}

function elapsedTime(date){
    const start= new Date(date);
    const end = new Date();

    const gap = (end - start) / 1000;
    const times = [
        {name:"년", time: 60 * 60 * 24 * 365},
        {name:"개월", time: 60 * 60 * 24 * 30},
        {name:"일", time: 60 * 60 * 24},
        {name:"시간", time: 60 * 60},
        {name:"분", time: 60},
    ]
    for(const time of times){
        const gapTime = Math.floor(gap / time.time);
        if(gapTime > 0){
            return `${gapTime}${time.name} 전
            `
        }
    }
    return "방금 전";
}




