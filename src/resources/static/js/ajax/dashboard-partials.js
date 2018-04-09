$(".allTasksPageBtn").click(loadAllTasksByPage(page));
function loadAllTasksByPage(page) {
    $.ajax({
        type: 'GET',
        url: '/tasks/all' + '?user=&page=' + page + '&size=4&partial=true',
        success: function (data) {
            $(".dynamicPanel").html("").append(data);
        }
    });
}