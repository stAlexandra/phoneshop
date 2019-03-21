function addToCart(phoneId) {
    var cartItemInfo = {
        phoneId: phoneId,
        quantity: $("#quantity" + phoneId).val()
    };

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
        url: "ajaxCart",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(cartItemInfo)
    }).done(function (response) {
        if(response.quantityError == null){
            document.getElementById("numItems").innerHTML = response.numItems;
            document.getElementById("totalPrice").innerHTML = response.totalPrice;
            document.getElementById("quantity" + phoneId).value = "";
            document.getElementById("quantityError" + phoneId).innerText = "";
        } else {
            document.getElementById("quantityError" + phoneId).innerText = response.quantityError.defaultMessage;
        }
    })
}
