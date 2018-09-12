$(function(){
    getAllNonReadNotifications();
});
function getAllNonReadNotifications() {
    $.ajax({
        type: 'GET',
        url: '/notifications/allnonreadnotifications',
        success: function (jsonData) {
            jsonData.forEach(function (result) {
                $.notify(result.message,
                    {
                        className : result.type.toLowerCase(),
                        globalPosition: 'bottom right',
                        autoHide: false
                    });
                console.log(result.message)
            })
        }
    });
}