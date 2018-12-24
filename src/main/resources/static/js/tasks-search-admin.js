$('#tasksSearchPanelBtn').onclick(showSearchBar);
function showSearchBar() {
    $('#tasksSearchBar').css("display", "block");
}

$('#closeSearchBarBtn').onclick(closeSearchBar);
function closeSearchBar() {
    $('#tasksSearchBar').css("display", "none");
}

$('#searchTasksBtn').onclick(searchTasks);
function searchTasks() {
    var dateFrom = $("input[name=date-from]").val();
    var dateTo = $("input[name=date-to]").val();
    var assigneeUsername = $("input[name=assignee]").val();
    $.ajax({
        type: 'GET',
        url: '/admin/tasks_repositories/search?dateFrom=' + dateFrom + '&dateTo=' + dateTo + '&assignee=' + assigneeUsername,
        success: function (data) {
            $("#dynamic").html("").append(data);
        }
    });
}