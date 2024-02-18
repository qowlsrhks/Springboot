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
    return{getList: getList};
})();

replyService.getList(showList);

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
                      <a class="modify" style="display: none; cursor: pointer">수정 완료</a>
                      &nbsp;&nbsp;<a class="remove">삭제</a>
                  </div>
              </div>
              <div style="display: flex; justify-content: space-between">
                <p class="reply-content">${reply.replyContent}</p>
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
            return `${gapTime}${time.name} 전`
        }
    }
    return "방금 전";

    }


