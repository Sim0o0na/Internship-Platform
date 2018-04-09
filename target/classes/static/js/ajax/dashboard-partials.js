$(".userTasksPageBtn").click(loadTasksByPageAndUser(page, username, urlPrefix));
function loadTasksByPageAndUser(page, username, urlPrefix) {
    if (urlPrefix === undefined) {
        urlPrefix = "";
    }
    $.ajax({
        type: 'GET',
        url: urlPrefix + '/tasks/all/' + '?user=' + username +'&page=' + page + '&size=4&partial=true',
        success: function (data) {
            $(".dynamicPanel").html("").append(data);
        }
    });
}

$(".allTasksPageBtn").click(loadAllTasksByPage(page));
function loadAllTasksByPage(page) {
    $.ajax({
        type: 'GET',
        url: '/tasks/all' + '?page=' + page + '&size=4&partial=true',
        success: function (data) {
            $(".dynamicPanel").html("").append(data);
        }
    });
}