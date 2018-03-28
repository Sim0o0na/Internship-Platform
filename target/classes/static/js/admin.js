$("#tasks").click(loadAllTasks());
function loadAllTasks() {
    $.ajax({
        type: 'GET',
        url: '/admin/tasks',
        success: function (data) {
            $("#dynamic").html("").append(data);
        }
    });
}

$("#createTask").click(loadAllTasks());
function loadCreateTaskForm() {
    $.ajax({
        type: 'GET',
        url: '/admin/tasks/create',
        success: function (data) {
            $("#taskPanel").html("").append(data);
        }
    });
}

$("#allTasks").click(loadAllTasks());
function loadAllTasksInPanel() {
    $.ajax({
        type: 'GET',
        url: '/admin/tasks/all',
        success: function (data) {
            $("#taskPanel").html("").append(data);
        }
    });
}

$("#allTaskApplications").click(loadAllTaskApplications());
function loadAllTaskApplications() {
    $.ajax({
        type: 'GET',
        url: '/admin/tasks/applications',
        success: function (data) {
            $("#taskPanel").html("").append(data);
        }
    });
}

$("#users").click(loadAllUsers());
function loadAllUsers() {
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