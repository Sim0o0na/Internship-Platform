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

function checkForLogin() {
    var param = getParameterByName('showloginform', window.location.href);
    console.log(param);
    if (param === "true") {
        $(".modal").modal('show');
        loadLoginForm();
    }
}


function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}