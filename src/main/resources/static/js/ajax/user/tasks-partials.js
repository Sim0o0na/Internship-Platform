$(".allTasksPageBtn").click(loadAllTasksByPage);
function loadAllTasksByPage(page) {
    $.ajax({
        type: 'GET',
        url: '/tasks_repositories/all' + '?user=&page=' + page + '&size=4&partial=true',
        success: function (data) {
            $(".dynamicPanel").html("").append(data);
        }
    });
}