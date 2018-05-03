$("#taskApplications").click(loadTaskApplicationsPanel);
function loadTaskApplicationsPanel() {
    console.log("task app panel");
    $.ajax({
        type: 'GET',
        url: '/admin/tasks/applications',
        success: function (data) {
            $("#taskPanel").html("").append(data);
        }
    });
}

$("#allTaskApplications").click(loadAllTaskApplications);
function loadAllTaskApplications() {
    $.ajax({
        type: 'GET',
        url: '/admin/tasks/applications/all',
        success: function (data) {
            $("#taskApplicationsPanel").html("").append(data);
        }
    });
}