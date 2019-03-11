function addToCart(phoneId) {
    var cartInfo = {
        phoneId: phoneId,
        quantity: +($("#quantity" + phoneId).val())
    };
    for(let i = 0; i<document.getElementsByName("quantityError").length; i++){
        document.getElementsByName("quantityError")[i].innerText = "";
    }

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
        url: "ajaxCart",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(cartInfo)
    }).done(function (response) {
        if(response.quantityError == null){
            document.getElementById("numItems").innerHTML = response.numItems;
            document.getElementById("totalPrice").innerHTML = response.totalPrice;
            document.getElementById("quantity" + phoneId).value = "";
        } else {
            document.getElementById("quantityError" + phoneId).innerText = response.quantityError.defaultMessage;
        }
    })
}
