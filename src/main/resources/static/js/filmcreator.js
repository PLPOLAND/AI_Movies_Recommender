jQuery(document).ready(function ($) {
    $("#zdjecie").focusout(function () {
        $("#zdj").html("<img src=\""+ $("#zdjecie").val() +"\">");
    });
    
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
                    alert("Dodawany film już istnieje")
                }
                else{
                    $("#tytul").val("");
                    $("#zdjecie").val("");
                    $("#gatunek").val("");
                    $("#rokProdukcji").val("");
                    $("#zdj").html("");
                    $("body").css("background-color", "#11ff11");
                    setTimeout(function () { $("body").css("background-color", "#ffffff");},500);
                }
            },
            error: function () {
                alert("wystąpił błąd ajaxa")
            }
        })
    });

    

});