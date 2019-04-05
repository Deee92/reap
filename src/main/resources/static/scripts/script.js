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


    $("#searchRecognitions").submit(function (e) {
        e.preventDefault();
        var form = $(this);
        $.ajax({
            type: 'GET',
            url: '/searchRecognitionByName',
            data: form.serialize(),
            success: function (data) {
                console.log(data);
                // var htmlFound = ($("#foundRecognitions").html());
                // $("#foundRecognitions").empty();
                // console.log($("#foundRecognitions").html());
                data.forEach(function (recognition) {
                    $('#receiverFound').text(recognition.receiverName);
                    $('#badgeFound').text(recognition.badge);
                    $('#senderFound').text(recognition.senderName);
                    $('#reasonFound').text(recognition.reason);
                    $('#commentFound').text(recognition.comment);
                    $('#dateFound').text(recognition.date);
                });
                // $("#foundRecognitions").htmlFor()
                $("#foundRecognitions").show();


            }
        })
    })
});
