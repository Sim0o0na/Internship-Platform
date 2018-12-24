$("#tasks_repositories").click(loadTasksPanel);
function loadTasksPanel() {
    $.ajax({
        type: 'GET',
        url: '/admin/tasks',
        success: function (data) {
            $("#dynamic").html("").append(data);
        }
    });
    loadTasksByPageForAdmin(0);
}

$(".adminTasksPageBtn").click(loadTasksByPageForAdmin);
function loadTasksByPageForAdmin(page) {
    $.ajax({
        type: 'GET',
        url: '/admin/tasks/all' + '?page=' + page + '&size=4&partial=true',
        success: function (data) {
            $(".dynamicPanel").html("").append(data);
        }
    });
}

$("#createTask").click(loadCreateTaskForm);
function loadCreateTaskForm() {
    $.ajax({
        type: 'GET',
        url: '/admin/tasks/create',
        success: function (data) {
            $("#modalContent").html("").append(data);
        }
    });
}

$("#allTasks").click(loadAllTasksInPanel);
function loadAllTasksInPanel() {
    $.ajax({
        type: 'GET',
        url: '/admin/tasks/all',
        success: function (data) {
            $("#taskPanel").html("").append(data);
        }
    });
}

$(".taskPageBtn").click(loadTasksByPage);
function loadTasksByPage(page) {
    $.ajax({
        type: 'GET',
        url: '/admin/tasks/all?page=' + page + '&size=4',
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
            $("#modalContent").html("").append(data);
        }
    });
}
