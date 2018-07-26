function showNotificationBar() {
    $("#showNotifications").on("click", function() {
        $("#notifications").show(800);
        $(".notification-button").css("color", "orange")
    })
}

function hideNotificationBar() {
    $("#hideNotificationBar").on("click", function() {
        $("#notifications").hide(800);
        $(".notification-button").css("color", "darkblue")
    })
}