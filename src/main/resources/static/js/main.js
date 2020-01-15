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


var films = null;
var liked = null;
var unliked = null;
var start = 0, end = 60;

function seefilms() {
    films = null;
    $.ajax({
        url: '/api/allf',
        type: 'post',
        data: {},
        success: function (response) {
            films = response;
            //Kolorownie nie polubionych
           $.ajax({
                url: '/api/getUnliked',
                    type: 'post',
                        data: { },
                success: function (response) {
                    unliked = response;
                    colorDisLiked();
                }
            });
            //Kolorownie polubionych
           $.ajax({
                url: '/api/getLiked',
                type: 'post',
                data: {},
                success: function (response) {
                    liked = response;
                    colorLiked();
                    console.log(liked);
                }
            });
            window.setTimeout(seef(),5);
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
            }
            else if(response == false){
                $(pole).parent().removeClass('liked');
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
function info(idF) {
    window.location = '/film?ID=' + idF;
}




function seef() {
    var array = films.slice(start,end);
    array.forEach(element => {
        var pos = $('<div class="film" id="f' + element.id + '">' +
            '<div class= "film-img" style = "background-image: url(' + element.zdjecie + ')" >' +
            '<div class="ocena">' +
            '<div class="ocena-icon">' +
            '<i class="icon icon-like" onclick="likeFilm(' + element.id + ',this)"></i>' +
            '</div>' +
            '<div class="ocena-icon">' +
            '<i class="icon icon-dislike" onclick="unLikeFilm(' + element.id + ',this)"></i>' +
            '</div>' +
            '<div class="ocena-icon">' +
            '<i class="icon icon-list" onclick="info(' + element.id + ')"></i>' +
            '</div>' +
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
    if(liked != null)
        colorLiked();
    if(unliked != null)
        colorDisLiked();
    start = end;
    if (end != films.length) {
        
        end = films.length;
            setTimeout(seef,10);
    }

    // if ((end + 60) <= films.length ) {
    //     // end += 60;
    //     console.log(end);
    //     setTimeout(seef,10);
        
    // }
    // else if(end + 1 <= films.length ){
    //     end ++;
    //     setTimeout(seef,10);
    // }
}

function colorLiked() {
    if (liked != null) {
        liked.forEach(element => {
            var text = '#f' + element;
            $($(text).children().children().children().get(0)).addClass('liked');
        });
    }
}
function colorDisLiked() {
    if (unliked != null && unliked != []) {
        unliked.forEach(element => {
            var text = '#f' + element;
            $($(text).children().children().children().get(1)).addClass('unliked');
        });
    }
}