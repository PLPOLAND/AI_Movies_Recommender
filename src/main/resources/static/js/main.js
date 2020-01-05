$(document).ready(function () {
    $('.banner-icons-etc').hide();
    $('.cover').click(function () {//pokazywanie/chowanie rozwijanego banneru w banerze
        $('.banner-icons-etc').toggle('slow');
    });
    $(".film").hover(
        function () {
            $('.ocena', this).fadeIn("fast")
        },
        function () {
            $('.ocena', this).fadeOut("fast");
        }
    );
    $('.ocena').fadeOut(0);

    if (window.location.pathname == "/") {
        seefilms();
    }
});

function seefilms() {
    $.ajax({
        url: '/api/allf',
        type: 'post',
        data: {},
        success: function (response) {
            response.forEach( element => {
                var pos = $('<div class="film" id="f'+ element.id +'">'+
                                '<div class= "film-img" style = "background-image: url('+element.zdjecie+')" >'+
                                    '<div class="ocena">'+
                                        '<div class="ocena-icon">'+
                                            '<i class="icon icon-like" onclick="likeFilm('+element.id+',this)"></i>'+
                                        '</div>'+
                                        '<div class="ocena-icon">'+
                                            '<i class="icon icon-dislike" onclick="unLikeFilm('+element.id+',this)"></i>'+
                                        '</div>'+
                                    '</div>'+
                                '</div >'+
                            '</div >');
                $('#mainbody').append(pos);
                $(".film").hover(
                    function () {
                        $('.ocena', this).fadeIn("fast")
                    },
                    function () {
                        $('.ocena', this).fadeOut("fast");
                    }
                );
                $('.ocena').fadeOut(0);
            });

            // var msg = "";
            // if (response == 1) {
            //     window.location = "/";
            // } else {
            //     msg = "Podano błędny login lub hasło";
            //     $("#err-msg").html(msg);
            //     $("#err-msg").show('slow');
            // }

        }
    });

    $.ajax({
        url: '/api/getUnliked',
        type: 'post',
        data: {},
        success: function (response) {
            response.forEach(element => {
                console.log($("#f" + element).children());
                
            });
        }
    });

}

function likeFilm(idF, pole) {
    $.ajax({
        url: '/api/likeFilm',
        type: 'get',
        data: { idF: idF },
        success: function (response) {
            if (response == true) {
                $(pole).addClass('liked');
                
                $($(pole).parent().parent().children().children().get(1)).removeClass('unliked');
            }
            else if(response == false){
                $(pole).removeClass('liked');
            }
        }
    });
}

function unLikeFilm(idF, pole) {
    $.ajax({
        url: '/api/unLikeFilm',
        type: 'get',
        data: { idF: idF },
        success: function (response) {
            if (response == true) {
                $(pole).addClass('unliked');
                $($(pole).parent().parent().children().children().get(0)).removeClass('liked');
            }
            else if (response == false) {
                $(pole).removeClass('unliked');
            }
        }
    });
}