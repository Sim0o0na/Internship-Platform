$("#applications").click(loadUserApplicationsPanel);
function loadUserApplicationsPanel() {
    $.ajax({
        type: 'GET',
        url: '/admin/users/applications/allwaiting',
        success: function (data) {
            $("#dynamic").html("").append(data);
        }
    });
}

$("#view-more").click(loadFullApplicationByUsername);
function loadFullApplicationByUsername(username) {
    $.ajax({
        type: 'GET',
        url: '/admin/users/applications/' + username,
        success: function (data) {
            $("#modalContent").html("").append(data);
        }
    });
}

