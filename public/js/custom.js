function sendDeleteRequest(url, rUrl) {
    $.ajax({
        url: url,
        method: "DELETE",
        success: function () {
            window.location = rUrl;
        },
        error: function () {
            window.location.reload()
        }

    })

}

