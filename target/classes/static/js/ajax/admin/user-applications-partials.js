$("#view-more").click(loadFullApplicationByUsername);
function loadFullApplicationByUsername() {
    var username = $("#user-application-username").text();
    $.ajax({
        type: 'GET',
        url: '/admin/users/applications/' + username,
        success: function (data) {
            $("#modalContent").html("").append(data);
        }
    });
}