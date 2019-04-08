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
                document.write(data)
            },
            error: function (data) {
                alert("failed")
            }
        });
    });

    /*
        $("#searchRecognitions").submit(function (e) {
            e.preventDefault();
            var form = $(this);
            $.ajax({
                type: 'POST',
                url: '/searchRecognitionByName',
                data: form.serialize(),
                success: function (data) {
                    console.log(data);
                    // var htmlFound = ($("#foundRecognitions").html());
                    $("#foundRecognitions").empty();
                    // console.log($("#foundRecognitions").html());
                    /*
                    data.forEach(function (recognition) {
                        $('#receiverFound').text(recognition.receiverName);
                        $('#badgeFound').text(recognition.badge);
                        $('#senderFound').text(recognition.senderName);
                        $('#reasonFound').text(recognition.reason);
                        $('#commentFound').text(recognition.comment);
                        $('#dateFound').text(recognition.date);
                    });

                    data.forEach(function (e) {
                        $("#foundRecognitions").append("<strong> " + e.receiverName + "</strong> has received " +
                            e.badge + " from " + e.senderName + " on " + e.date + " for " + e.reason + "<br>")
                    })
                    // $("#foundRecognitions").htmlFor()
                    $("#foundRecognitions").show();
                    $("#recognizedUserName").val("");
                }
            })
        })
        */

    $("#searchRecognitions").submit(function (e) {
        e.preventDefault();
        var form = $(this);
        var users = $.ajax({
            type: 'POST',
            url: '/searchRecognitionByName',
            dataType: 'json',
            data: form.serialize(),
            success: function (data) {
                $("#userdataDiv").empty()
                console.log(data[0].receiverName);
                console.log(JSON.stringify(data));
                data.forEach(function (e) {
                    console.log(e.receiverName)
                    $("#userdataDiv").append("<strong> " + e.receiverName + "</strong> has received " +
                        e.badge + " from " + e.senderName + " on " + e.date + " for " + e.reason + "<br>")
                })
                $("#recognizedUserName").val("");
            }
        })
    })

});
