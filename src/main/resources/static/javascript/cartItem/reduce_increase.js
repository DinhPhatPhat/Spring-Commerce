document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".increaseButton").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.getAttribute("data-id");
            increase(id);
        });
    });

    document.querySelectorAll(".reduceButton").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.getAttribute("data-id");
            reduce(id);
        });
    });
});

function increase(id) {
    axios.post('/api/cartItem/increase', { id })
        .then(() => location.reload())
        .catch(() => alert("Lỗi tăng số lượng"));
}

function reduce(id) {
    axios.post('/api/cartItem/reduce', { id })
        .then(() => location.reload())
        .catch(() => alert("Lỗi giảm số lượng"));
}
