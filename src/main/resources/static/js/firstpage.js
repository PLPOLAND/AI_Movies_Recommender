var liked = 0;

$(document).ready(function () {
    seefilms();
    // $(".icon").click(function(){
    //     console.log(liked);
    //     if(liked>=1){
    //         $(".show").css("display","block");
    //     }
    //     else{
    //         $(".show").css("display", "none");
    //     }
    // });
    window.setInterval(function () {
        if (liked >= 4) {
            $(".show").css("display", "block");
            $(".show").html("Pokaż proponowane");
        }
        else {
            $(".show").css("display", "none");
        }
    },500);
});



function seefilms() {
    $.ajax({
        url: '/api/allf',
        type: 'post',
        data: {},
        success: function (response) {
            response.forEach(element => {
                var pos = $('<div class="film" id="f' + element.id + '">' +
                    '<div class= "film-img" style = "background-image: url(' + element.zdjecie + ')" >' +
                    '<div class="ocena">' +
                    '<div class="ocena-icon">' +
                    '<i class="icon icon-like" onclick="likeFilm(' + element.id + ',this)"></i>' +
                    '</div>' +
                    '<div class="ocena-icon">' +
                    '<i class="icon icon-dislike" onclick="unLikeFilm(' + element.id + ',this)"></i>' +
                    '</div>' +
                    '</div>' +
                    '</div >' +
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
            //Kolorownie nie polubionych
            $.ajax({
                url: '/api/getUnliked',
                type: 'post',
                data: {},
                success: function (response) {
                    response.forEach(element => {
                        var text = '#f' + element;
                        $($(text).children().children().children().get(1)).addClass('unliked');
                    });
                }
            });
            //Kolorownie polubionych
            $.ajax({
                url: '/api/getLiked',
                type: 'post',
                data: {},
                success: function (response) {
                    response.forEach(element => {
                        var text = '#f' + element;
                        $($(text).children().children().children().get(0)).addClass('liked');
                    });
                }
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
                $(pole).parent().addClass('liked');
                $($(pole).parent().parent().children().get(1)).removeClass('unliked');
                liked = liked + 1;
            }
            else if (response == false) {
                $(pole).parent().removeClass('liked');
                liked = liked - 1;
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
                $(pole).parent().addClass('unliked');
                $($(pole).parent().parent().children().get(0)).removeClass('liked');
            }
            else if (response == false) {
                $(pole).parent().removeClass('unliked');
            }
        }
    });
}