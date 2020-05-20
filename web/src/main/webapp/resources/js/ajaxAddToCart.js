function addToCart(id) {
    var cartItemInfo = {
        id: id,
        quantity: $("#quantity" + id).val()
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
        document.getElementById("quantity" + id).value = "1";
        document.getElementById("quantityError" + id).innerText = "";
    }).fail(function (response) {
        document.getElementById("quantityError" + id).innerText = response.responseJSON.quantityErrorMessage;
    })
}
