$("#users").click(loadUsersPanel);
function loadUsersPanel() {
    $.ajax({
        type: 'GET',
        url: '/admin/users/all',
        success: function (data) {
            $("#dynamic").html("").append(data);
        }
    });
}

$("searchUser").click(searchUser);
function searchUser() {
    var username = $("#searchUserInput").val();
    $.ajax({
        type: 'POST',
        url: '/admin/users/search/' + username,
        success: function (data) {
            $("#usersPanel").html("").append(data);
        }
    });
}