// $(document).scroll(function() {
//     var y = $(this).scrollTop();
//     if (y > 300) {
//         $('.header-image').fadeIn();
//     } else {
//         $('.header-image').fadeOut();
//     }
// });

window.setTimeout(function() {
    $(".alert").fadeTo(500, 0).slideUp(500, function(){
        $(this).remove();
    });
}, 4000);