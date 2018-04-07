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

$(".edit-btn").click(loadEditTaskForm);
function loadEditTaskForm(taskId) {
    $.ajax({
        type: 'GET',
        url: '/admin/tasks/edit/' + taskId,
        success: function (data) {
            console.log(data);
            $("#modalContent").html("").append(data);
        }
    });
}