jQuery(document).ready(function ($) {
    $("#zdjecie").focusout(function () {
        $("#zdj").html("<img src=\""+ $("#zdjecie").val() +"\">");
    });
    $("input").click(function () {
        $("#info").html("");
    })
    
    $("#submit").click(function () {
        $.ajax({
            url: '/api/createFilm',
            type: 'post',
            data: {
                tytul: $("#tytul").val(),
                zdjecie: $("#zdjecie").val(),
                gatunek: $("#gatunek").val(),
                rok: $("#rokProdukcji").val()
            },
            success: function (response) {
                if(response == false){
                    $("#info").html("Dodawany film już istnieje");
                    $("#info").css("color","red");
                    ("body").css("background-color", "#11ff11");
                    setTimeout(function () { $("body").css("background-color", "#ffffff"); }, 2000);
                }
                else{
                    $("#info").html("Dodano nowy film");
                    $("#info").css("color","green");
                    $("#tytul").val("");
                    $("#zdjecie").val("");
                    $("#gatunek").val("");
                    $("#rokProdukcji").val("");
                    $("#zdj").html("");
                    $("body").css("background-color", "#11ff11");
                    setTimeout(function () { $("body").css("background-color", "#ffffff");},2000);
                }
            },
            error: function () {
                alert("wystąpił błąd ajaxa");
            }
        })
    });

    

});