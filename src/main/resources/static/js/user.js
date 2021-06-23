let index = {
    init: function () {
        $("#btn-save").on("click", () => { // this 바인딩을 위해 화살표함수로 사용
            this.save();
        });
    },

    save: function () {
        // alert('user - save')
        let data = {
            username: $("#username").val(),
            email: $("#email").val(),
            password: $("#password").val()
        }

        console.log(data);

        $.ajax({
            type: "POST",
            url: "/blog/api/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json" // 응답이 왔을 때 생긴게 json 이라면 javascript 오브젝트로 변환 (생략해도 서버가 json 으로 보내면 알아서 받을 수 있다.)
        }).done((resp) => {
            console.log(resp);
            alert("회원가입이 완료되었습니다.");
            location.href="/blog"
        }).fail((error) => {
            console.log(error);
        });
    }
}

index.init();