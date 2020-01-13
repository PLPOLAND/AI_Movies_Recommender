$(document).ready(function () {
    $("#subbutton").click(function () {
        var nickname = $("#nick").val();
        var username = $("#name").val();
        var userlastname = $("#lastname").val();
        var password = $("#pass").val();
        var confpassword = $("#confpass").val();
        var useremail = $("#email").val();
        var userconfemail = $("#confemail").val();

        if (nickname != "" && username != "" && userlastname != "" && password != "" && confpassword != "" && useremail != "" && userconfemail != "" &&
            password == confpassword && useremail == userconfemail)
        {
            $.ajax({
                url: '/api/register',
                type: 'get',
                data: { nick: nickname, name: username, lastname: userlastname, pass: password, confpass: confpassword,
                        email: useremail, confemail: userconfemail },
                success: function (response)
                {
                    $("#err-msg").html(response);
                    
                    if (response == 1)
                    {
                        window.location = "/firstpage";  
                    }
                }
            });
        }
        else
        {
            var msg = "";
                msg = "Pola nie mogą być puste"; 
                $("#err-msg").html(msg);
                

            if(password != confpassword)
            {
                $("#confpass").css("background-color", "#FF0000");
                msg = "Hasło i jego potwierdzenie różnią się"; 
                $("#err-msg").html(msg);
            }
     
            if (useremail != userconfemail)
            {
                $("#confemail").css("background-color", "#FF0000");
                msg = "Email i jego potwierdzenie róźnią się"; 
                $("#err-msg").html(msg);
            }
            $("#err-msg").show('slow');
        }
    });
    $(".container").click(function(){

        //$("#err-msg").hide('slow');
    })
});