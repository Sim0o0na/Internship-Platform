window.setTimeout(function() {
    $(".alert").fadeTo(500, 0).slideUp(500, function(){
        $(this).remove();
    });
}, 4000);

$('#close-modal-btn').onclick(closeModal);
function closeModal(){
    $("#modalContent").parent().fadeOut();
}


$('#login-btn').on('click', validateLoginUrl);
function validateLoginUrl() {
    var currentRequestUrl = $(location).attr('href');
    console.log(currentRequestUrl)
}

