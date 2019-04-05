$(window).on('load', function () {
    $('#exampleModalCenter').modal('show');
});

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
                alert("user successfully recognized")
                document.write(data)
            },
            error: function (data) {
                alert("failed")
            }
        });
    });
});
