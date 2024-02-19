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
        })
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
    return{getList: getList, write: write};
})();

replyService.getList(showList);


/*###### Event ########*/

$("ul.replies").on("click", "a.modify-ready", function () {
    let i = $("a.modify-ready").index(this);
    $(this).hide();
    $("a.remove").eq(i).hide();
    $(this).parents("div").append(`<a class="modify" style="cursor: pointer">수정완료</a>`);
    $(this).parents("div").append(`&nbsp;&nbsp;<a class="modify-cancel" style="cursor: pointer">취소</a>`);
});


$("ul.replies").on("click", "a.modify-cancel",function () {
    let i = $("a.modify-cancel").index(this);
    $(this).parents("div").remove("modify");
    $(this).parents("div").remove("modify-cancel");
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
    let text = "";
    replies.forEach(reply => {
        text += `
          <li style="display: block">
              <div style="display: flex; justify-content: space-between">
                    <strong style="display: block">${reply.replyWriter}</strong>
                  <div>
                      <a class="modify-ready" style="display: none; cursor: pointer">수정</a>
                      &nbsp;&nbsp;<a class="remove" style="cursor: pointer">삭제</a>
                  </div>
              </div>
              <div style="display: flex; justify-content: space-between">
                <p class="reply-content">${reply.replyContent}</p>
                <!--textarea class="reply-content-modify">${reply.replyContent}</textarea-->
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




