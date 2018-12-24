$(function() {
    $('#carousel-learn-more').click(function(){
        $('html, body').animate({scrollTop: $("#about-paragraph").offset().top - 270}, 1500);
    });
});