$(document).ready(function () {
    $("#logoutButton").click(function (e) {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            url: '/logout',
            success: function (data) {
                window.location.reload();
            },
            error: function () {
                console.log("Logout failed")
            }
        })
    })
})

$(document).ready(function () {
    $('input[type="file"]').change(function (e) {
        var fileName = e.target.files[0].name;
        $("#inputPhoto").text(fileName);
        console.log(fileName);
    });
});

$(document).ready(function (e) {
    $("#recognitionForm").submit(function (e) {
        e.preventDefault();
        var form = $(this);
        $.ajax({
            type: 'POST',
            url: '/recognize',
            data: form.serialize(),
            success: function (data) {
                // alert("User recognized!")
                // document.write(data)
                window.location.reload()
            },
            error: function (data) {
                alert("failed")
            }
        });
    });

    $("#searchRecognitionButton").click(function (e) {
        var form = $("#searchRecognitions");
        var users = $.ajax({
            type: 'POST',
            url: '/searchRecognitionByName',
            dataType: 'json',
            data: form.serialize(),
            success: function (data) {
                $("#recognitionResults").hide()
                $("#userdataDiv").empty()
                console.log(data[0].receiverName);
                console.log(JSON.stringify(data));
                data.forEach(function (e) {
                    $("#recognitionResults").show()
                    console.log(e.receiverName)
                    $("#userdataDiv").append(
                        "<strong> " +
                        e.receiverName +
                        "</strong> has received a " +
                        e.badge +
                        " from " +
                        e.senderName +
                        " for " +
                        e.reason +
                        " because " +
                        e.comment +
                        "<br>" +
                        " on " + e.date +
                        "<br>"
                    )
                });
                $("#recognizedUserName").val("");
            },
            error: function (data) {
                console.log("Failed to get recognized users")
            }
        });
    });
});
