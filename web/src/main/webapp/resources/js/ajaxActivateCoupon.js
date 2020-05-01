function activateCartCoupon() {
    // $("#activateCouponBtn").click(function () {
        var couponCode = $("#couponCodeInput").val();
        // $(".uploadEvent").show();
        // $('.itemLabelError').remove();

        $.ajax({
            method: "POST",
            url: "activateCoupon",
            data: {
                'couponCode' : couponCode
            }
        }).done(function () {
            window.location.href = "cart?couponActivated=true";
        }).fail(function () {
            window.location.href = "cart?couponActivated=false";
        })
    // });
}
