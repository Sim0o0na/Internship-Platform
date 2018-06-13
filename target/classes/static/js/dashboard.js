function showNotificationBar() {
    $("#showNotifications").on("click", function() {
        $("#notifications").show(800);
    })
}

function hideNotificationBar() {
    $("#hideNotificationBar").on("click", function() {
        $("#notifications").hide(800);
    })
}