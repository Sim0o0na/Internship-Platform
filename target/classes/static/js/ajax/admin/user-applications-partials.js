$("#view-more").click(loadFullApplicationByUsername);
function loadFullApplicationByUsername(username) {
    console.log(username);
    $.ajax({
        type: 'GET',
        url: '/admin/users/applications/' + username,
        success: function (data) {
            $("#modalContent").html("").append(data);
        }
    });
}