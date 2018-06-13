$(document).scroll(function() {
    var y = $(this).scrollTop();
    if (y > 300) {
        $('.header-image').fadeIn();
    } else {
        $('.header-image').fadeOut();
    }
});