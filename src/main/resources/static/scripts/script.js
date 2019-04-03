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
