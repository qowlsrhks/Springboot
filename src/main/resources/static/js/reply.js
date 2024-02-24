/*###### 모듈화 ########*/
let replyService = (function (){
    function getList (callback){
        $.ajax({
            url: `/replies/list/${boardId}/${replyPage}/${rowCount}`,
            dataType: "json",
            success: function (replies){
                if(callback){
                    callback(replies)
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

    function modify(index ,callback) {
        $.ajax({
            url: "/replies/modify",
            type: "post",
            data: JSON.stringify({replyContent: $("textarea.reply-content-modify").eq(index).val(),replyId: $("textarea.reply-content-modify").eq(index).closest("li").data("reply-id")}),
            contentType: "application/json;charset=utf-8",
            success: function (){
                if (callback) {
                    callback(index);
                }
            }
        });
    }
    return{getList: getList, write: write, modify: modify};
})();

replyService.getList(showList);


/*###### Event ########*/

    let check = false
$("ul.replies").on("click", "a.modify", function () {
    let i = $("a.modify").index(this);
    replyService.modify(i, update);
});

function update(i) {
    $("p.reply-content").text($("textarea.reply-content-modify").eq(i).val());
    $("a.modify-cancel").trigger("click");
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
    $(this).parent("div").find("a").remove("a.modify");
    $(this).parent("div").find("a").remove("a.modify-cancel");
    $("p.reply-content").eq(i).closest("div").find("textarea").remove("textarea.reply-content-modify");
    $("p.reply-content").eq(i).show();
    $("a.modify-ready").eq(i).show();
    $("a.remove").eq(i).show();
});

$("a.register").on("click", function () {
    $("div.register-form").show();
    $(this).hide();
});

$("a.cancel").on("click", function () {
    $("div.register-form").hide();
    $("a.register").show();
    $("input[name='replyWriter']").val("");
    $("textarea[name='replyContent']").val("");
});


/*###### DOM ########*/
$("a.finish").on("click", function () {
    replyService.write(register);
});
function register() {
    $("ul.replies").html("");
    $("a.cancel").trigger("click");
    replyService.getList(showList);
}


/*###### DOM ########*/
function showList(replies){
// <textarea class="reply-content-modify">${reply.replyContent}</textarea>
    let text = "";
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




