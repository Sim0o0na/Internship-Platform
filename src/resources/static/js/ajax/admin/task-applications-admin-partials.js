$("#taskApplications").click(loadTaskApplicationsPanel);
function loadTaskApplicationsPanel() {
    $.ajax({
        type: 'GET',
        url: '/admin/tasks_repositories/applications',
        success: function (data) {
            $("#taskPanel").html("").append(data);
        }
    });
}

$("#allTaskApplications").click(loadAllTaskApplications);
function loadAllTaskApplications() {
    $.ajax({
        type: 'GET',
        url: '/admin/tasks_repositories/applications/all',
        success: function (data) {
            $("#taskApplicationsPanel").html("").append(data);
        }
    });
}