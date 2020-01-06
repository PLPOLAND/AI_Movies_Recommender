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
            confpassword != "" && useremail !="" && userconfemail != "") {
            $.ajax({
                url: '/api/register',
                type: 'post',
                data: { nick: nickname, name: username, lasatname: userlastname, pass: password, confpass: confpassword,
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
    });
    $(".container").click(function(){

        $("#err-msg").hide('slow');
    })
});