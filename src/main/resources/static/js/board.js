// 정적 파일들은 static 하위 경로가 기본으로 되어있다.
let index = {
    init: function () {
        $("#btn-save").on("click", () => { // this 바인딩을 위해 화살표함수로 사용
            this.save();
        });
        $("#btn-delete").on("click", () => { // this 바인딩을 위해 화살표함수로 사용
            this.deleteById();
        });
        $("#btn-update").on("click", () => { // this 바인딩을 위해 화살표함수로 사용
            this.update();
        });
        $("#btn-reply-save").on("click", () => { // this 바인딩을 위해 화살표함수로 사용
            this.replySave();
        });
    },

    save: function () {
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        }

        console.log(data);

        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done((resp) => {
            console.log(resp);
            alert("글쓰기가 완료되었습니다.");
            location.href="/"
        }).fail((error) => {
            console.log(error);
        });
    },

    deleteById: function () {

        let id = $("#id").text();

        $.ajax({
            type: "DELETE",
            url: "/api/board/" + id,
            dataType: "json"
        }).done((resp) => {
            console.log(resp);
            alert("글삭제가 완료되었습니다.");
            location.href="/"
        }).fail((error) => {
            console.log(error);
        });
    },

    update: function () {

        let id = $("#id").val();

        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        }

        console.log(data);

        $.ajax({
            type: "PUT",
            url: "/api/board/" + id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done((resp) => {
            console.log(resp);
            alert("글수정이 완료되었습니다.");
            location.href="/"
        }).fail((error) => {
            console.log(error);
        });
    },

    replySave: function () {
        let data = {
            userId: $("#userId").val(),
            boardId : $("#boardId").val(),
            content: $("#reply-content").val()
        }
        let boardId = $("#boardId").val();
        console.log(data);

        $.ajax({
            type: "POST",
            url: `/api/board/${boardId}/reply`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done((resp) => {
            console.log(resp);
            alert("댓글 작성이 완료되었습니다.");
            location.href=`/board/${boardId}`
        }).fail((error) => {
            console.log(error);
        });
    },

    replyDelete: function (boardId, replyId) {

        $.ajax({
            type: "DELETE",
            url: `/api/board/${boardId}/reply/${replyId}`,
            dataType: "json"
        }).done((resp) => {
            console.log(resp);
            alert("댓글 삭제가 완료되었습니다.");
            location.href=`/board/${boardId}`
        }).fail((error) => {
            console.log(error);
        });
    },
}

index.init();