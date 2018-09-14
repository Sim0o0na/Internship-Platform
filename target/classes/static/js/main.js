window.setTimeout(function() {
    $(".alert").fadeTo(500, 0).slideUp(500, function(){
        $(this).remove();
    });
}, 4000);

// Pusher notifications
$(function() {
    var pusher = new Pusher('8ced822414ccc92c355c', {
        cluster: 'eu',
        forceTLS: true
    });

    var channel = pusher.subscribe('my-channel');
    channel.bind('my-event', function (notification) {
        new Noty({
            text: notification.message,
            type: 'info',
            layout: "bottomRight",
            theme: 'relax',
            callbacks: {
                onClose: function () {
                    $.ajax({
                        type: 'POST',
                        url: '/notifications/markreadbyid/' + result.id
                    })
                }
            }
        }).show();
    });
});

function checkIfLoginFormHasToBeShown() {
    var param = getParameterByName('showloginform', window.location.href);
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