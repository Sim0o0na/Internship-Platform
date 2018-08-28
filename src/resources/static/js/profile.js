$(".change-password-btn").click(loadChangePasswordForm);
function loadChangePasswordForm() {
    $.ajax({
        type: 'GET',
        url: '/profile/changepassword',
        success: function (data) {
            $("#modalContent").html("").append(data);
        }
    });
}

$("#load-edit-form-btn").click(loadUserEditForm);
function loadUserEditForm() {
    $.ajax({
        type: 'GET',
        url: '/profile/edit',
        success: function (data) {
            $("#modalContent").html("").append(data);
        }
    });
}