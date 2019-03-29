function addToCart(phoneId) {
    var cartItemInfo = {
        phoneId: phoneId,
        quantity: $("#quantity" + phoneId).val()
    };


    $.ajax({
        method: "POST",
        url: "ajaxCart",
        dataType: "json",
        data: cartItemInfo
    }).done(function (response) {
        document.getElementById("numItems").innerHTML = response.numItems;
        document.getElementById("totalPrice").innerHTML = response.totalPrice
            .toLocaleString("en-US", {style: "currency", currency: "USD", minimumFractionDigits: 2});
        document.getElementById("quantity" + phoneId).value = "1";
        document.getElementById("quantityError" + phoneId).innerText = "";
    }).fail(function (response) {
        document.getElementById("quantityError" + phoneId).innerText = response.responseJSON.quantityErrorMessage;
    })
}
