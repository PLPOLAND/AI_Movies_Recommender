$(document).ready(function () {
    $('.banner-icons-etc').hide();
    $('.cover').click(function () {//pokazywanie/chowanie rozwijanego banneru w banerze
        $('.banner-icons-etc').toggle('slow');
    });
});