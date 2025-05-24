document.addEventListener("DOMContentLoaded", () => {

    const addToCartBtn = document.getElementById("addToCartButton")
    if (addToCartBtn) {
        addToCartBtn.addEventListener('click', () => {
            addToCart()
        })
    }
})

function addToCart() {
    const productId = document.getElementById("id").value;

    axios.post('/api/product/addToCart', {
        id: productId,
    })
        .then(response => {
            if (response.status === 200) {
                showSuccess(response.data, 3000);
            } else {
                console.log(response);
                showError("Lỗi không xác định")
            }
        })
        .catch(error => {
            showError(error.response.data)
        });
}

