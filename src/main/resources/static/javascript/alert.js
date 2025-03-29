let alertFailed
let alertOk

document.addEventListener("DOMContentLoaded", () => {
    alertFailed = document.getElementById("alert-failed")
    alertOk = document.getElementById("alert-ok")
})

function showError(message, timeout = 5000) {
    const alertFailed = document.getElementById("alert-failed");
    const alertOk = document.getElementById("alert-ok");

    alertOk.classList.remove("show");
    alertFailed.textContent = message;
    alertFailed.classList.add("show");

    setTimeout(() => {
        alertFailed.classList.remove("show");
    }, timeout);
}

function showSuccess(message, timeout = 5000) {
    const alertFailed = document.getElementById("alert-failed");
    const alertOk = document.getElementById("alert-ok");

    alertFailed.classList.remove("show");
    alertOk.textContent = message;
    alertOk.classList.add("show");

    setTimeout(() => {
        alertOk.classList.remove("show");
    }, timeout);
}