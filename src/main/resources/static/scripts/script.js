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
            success: function (data, status, xhr) {
                // alert("User recognized!")
                // document.write(data)
                // window.location.reload()
                setTimeout(location.reload.bind(location), 1500)
                var x = xhr.getResponseHeader("myResponseHeader");
                if (x === "doesNotExist") {
                    $("#errorAlert").append(data)
                    $("#errorAlert").show()
                }
                if (x === "selfRecognition") {
                    $("#selfRecognitionAlert").append(data)
                    $("#selfRecognitionAlert").show()
                }
                if (x === "successfulRecognition") {
                    $("#successAlert").append(data)
                    $("#successAlert").show()
                }
            },
            error: function (data) {
                alert("failed")
            }
        });
    });

    $("#successAlert").click(function () {
        window.location.reload();
    });

    $("#errorAlert").click(function () {
        window.location.reload();
    });

    $("#selfRecognitionAlert").click(function () {
        window.location.reload();
    });

    $("#searchRecognitions").submit(function (e) {
        e.preventDefault();
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
                console.log("Failed to get recognized users by name")
            }
        });
    });

    $("#dateToday").click(function (e) {
        console.log("Today")
        searchDates("today")
    });

    $("#dateYesterday").click(function (e) {
        console.log("Yesterday")
        searchDates("yesterday")
    });

    $("#dateLast7Days").click(function (e) {
        console.log("Last 7")
        searchDates("last7")
    });

    $("#dateLast30Days").click(function (e) {
        console.log("Last 30")
        searchDates("last30")
    });

    function searchDates(dateString) {
        console.log("Function called " + dateString)
        console.log(dateString)
        $.ajax({
            type: 'GET',
            url: '/searchRecognitionsByDate/' + dateString,
            success: function (data) {
                console.log("after controller");
                $("#recognitionResults").hide()
                $("#userdataDiv").empty()
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
            },
            error: function () {
                console.log("failed to get recognized users by date");
            }
        })
    }

    $("#recognitionForm").submit(function (e) {
        e.preventDefault();
    });

    $("#recognizedUserName").autocomplete({
        source: function (request, response) {
            $.ajax({
                method: 'GET',
                url: "/autocomplete",
                data: {"pattern": $("#recognizedUserName").val()},
                success: function (data) {
                    var availableUsers = [];
                    data.forEach(function (e) {
                        availableUsers.push(e.fullName)
                    });
                    response(availableUsers);
                }
            })
        }
    });

    $("#userNameToRecognize").autocomplete({
        source: function (request, response) {
            $.ajax({
                method: 'GET',
                url: "/autocomplete",
                data: {"pattern": $("#userNameToRecognize").val()},
                success: function (data) {
                    var availableUsers = [];
                    data.forEach(function (e) {
                        availableUsers.push(e.fullName)
                    });
                    response(availableUsers);
                }
            })
        }
    });

    $(".revokeRecognitionButtonClass").click(function (e) {
        var answer = confirm("Are you sure you want to revoke this recognition?");
        if (answer == true) {
            var recognitionId = $(this).closest(".recognitionIdClass").find("input[name='recognitionIdToRevoke']").val();
            $.ajax({
                method: 'PUT',
                url: "/recognitions/" + recognitionId,
                success: function (data) {
                    window.location.reload();
                }
            })
        } else {
            console.log("Will not revoke recognition")
        }
    });

    $(".addItemToCartButton").click(function (e) {
        var itemId = $(this).closest(".itemRow").find("input[name='itemId']").val();
        var userId = $(this).closest(".itemRow").find("input[name='userId']").val();
        $.ajax({
            method: 'POST',
            url: "/addToCart/" + itemId,
            success: function (data, status, xhr) {
                // alert("Item added to cart");
                setTimeout(location.reload.bind(location), 1500);
                var x = xhr.getResponseHeader("myResponseHeader");
                if (x === "insufficientPoints") {
                    $("#errorCartAlert").append(data);
                    $("#errorCartAlert").show();
                }
                if (x === "cartAddSuccessful") {
                    $("#successCartAlert").append(data);
                    $("#successCartAlert").show();
                }

            }
        })
    });

    $("#successCartAlert").click(function () {
        window.location.reload();
    });

    $("#errorCartAlert").click(function () {
        window.location.reload();
    });

    $(".removeCartItemButton").click(function (e) {
        var itemId = $(this).closest(".cartRow").find("input[name='cartItemId']").val();
        console.log("Item " + itemId + " will be removed")
        $.ajax({
            method: 'PUT',
            url: "/removeFromCart/" + itemId,
            success: function (data) {
                window.location.reload();
            }
        })
    })
});

