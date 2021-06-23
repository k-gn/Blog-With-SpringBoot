let index = {
    init: function () {
        $("#btn-save").on("click", () => {
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

        $.ajax().done().fail();
    }
}

index.init();