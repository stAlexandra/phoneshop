<%@ page contentType="text/html;charset=UTF-8" %>
<div class="row">
    <div class="col-10">
        <h1 class="display-3">Phonify</h1>
    </div>
    <div class="col-2">
        <div id="minicart" class="card">
            <div class="card-body">
                <h4>My cart</h4>
                <p>
                    <span id="numItems">${cartNumItems}</span> items <span id="totalPrice">${cartTotalPrice}</span>$
                </p>
                <input type="button" class="btn btn-outline-primary align-self-end" value="Go to cart">
            </div>
        </div>
    </div>
</div>