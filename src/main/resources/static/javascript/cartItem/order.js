document.addEventListener("DOMContentLoaded", () => {
    const orderBtn = document.getElementById("orderButton")
    if (orderBtn) { orderBtn.addEventListener("click", () => {
        order();
    })}
})

function order() {
    const cartItems = document.querySelectorAll('.cart-item');
    const cartItemIds = Array.from(cartItems).map(item => item.getAttribute('data-id'));
    const recipientName = document.getElementById("recipientName").value
    const recipientPhoneNumber = document.getElementById("recipientPhoneNumber").value
    const shippingAddress = document.getElementById("shippingAddress").value

    if (cartItemIds.length === 0) {
        showError("Không có sản phẩm nào trong giỏ hàng", 5000);
        return;
    }
    if (!recipientName.trim() || !recipientPhoneNumber.trim() || !shippingAddress.trim()) {
        showError("Vui lòng điền đầy đủ thông tin giao hàng", 5000);
        return;
    }

    const orderData = {
        cartItemIds: cartItemIds,
        recipientName: recipientName,
        recipientPhoneNumber: recipientPhoneNumber,
        shippingAddress: shippingAddress
    };

    axios.post('/api/order', orderData)
        .then(response => {
            showSuccess(response.data, 3000);
            setTimeout(() => {
                window.location.reload();
            }, 3000);
        })
        .catch(error => {
            console.error(error);
            showError(error.response?.data || "Đặt hàng thất bại", 3000);
        });
}
