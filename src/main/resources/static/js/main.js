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
                var pos = $('<div class="film">'+
                                '<div class= "film-img" style = "background-image: url('+element.zdjecie+')" >'+
                                    '<div class="ocena">'+
                                        '<div class="ocena-icon">'+
                                            '<i class="icon icon-like"></i>'+
                                        '</div>'+
                                        '<div class="ocena-icon">'+
                                            '<i class="icon icon-dislike"></i>'+
                                        '</div>'+
                                    '</div>'+
                                '</div >'+
                                // '<div class="film-title">'+
                                //     element.tytul +
                                // '</div>'+
                                // '<div class="film-gatunek">'+
                                //     element.gatunek +
                                // '</div>'+
                                // '<div class="film-rok">'+
                                //     element.rokProdukcji +
                                // '</div>'+
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
}