
$(function(){
    $.ajax({
        type: 'GET',
        url: '/notifications/hasunreadnotifications',
        success: function (ajaxResult) {
            if (ajaxResult === true) {
                getAllNonReadNotifications();
            }
        }
    });
});

function getAllNonReadNotifications() {
    $.ajax({
        type: 'GET',
        url: '/notifications/allnonreadnotifications',
        success: function (jsonData) {
            jsonData.forEach(function (result) {
                console.log(result);
                new Noty({
                    id: result.id,
                    text: result.message,
                    type: result.type.toLowerCase(),
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
            })
        }
    });
}