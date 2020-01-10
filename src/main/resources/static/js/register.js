$(document).ready(function () {
    $("#subbutton").click(function () {
        var nickname = $("#nick").val();
        var username = $("#name").val();
        var userlastname = $("#lastname").val();
        var password = $("#pass").val();
        var confpassword = $("#confpass").val();
        var useremail = $("#email").val();
        var userconfemail = $("#confemail").val();

        if (nickname != "" && username != "" && userlastname != "" && password != "" &&
            confpassword != "" && useremail !="" && userconfemail != "" && password == confpassword && email == confemail) {
            $.ajax({
                url: '/api/register',
                type: 'get',
                data: { nick: nickname, name: username, lastname: userlastname, pass: password, confpass: confpassword,
                        email: useremail, confemail: userconfemail },
                success: function (response) {
                    $("#err-msg").html(response);
                    
                    var msg = "";
                    if (response == 1) {
                        window.location = "/";
                    } else {
                        msg = "Podano błędne dane"; 
                        $("#err-msg").html(msg);
                        $("#err-msg").show('slow');
                    }
                    
                }
            });
        }
        else
        {
            if(password != confpassword)
                $("#confpass").css("background-color", "#FF0000");
            if(email != confemail)
                $("confemail").css("background-color", "#FF0000");
        }
    });
    $(".container").click(function(){

        $("#err-msg").hide('slow');
    })
});