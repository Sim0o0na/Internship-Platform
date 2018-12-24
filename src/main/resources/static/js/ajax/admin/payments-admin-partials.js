$("#payments").click(loadPaymentsPanel);
function loadPaymentsPanel() {
    $.ajax({
        type: 'GET',
        url: '/admin/payments/all',
        success: function (data) {
            $("#dynamic").html("").append(data);
        }
    });
}