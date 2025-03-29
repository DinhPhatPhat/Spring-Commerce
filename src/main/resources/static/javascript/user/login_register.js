
document.addEventListener("DOMContentLoaded", () => {

    const registerBtn = document.getElementById('registerButton')
    if (registerBtn) {
        registerBtn.addEventListener('click', () => {
            submitRegisterForm()
        })
    }

    const changePasswordBtn = document.getElementById('changePasswordButton')
    if (changePasswordBtn) {
        changePasswordBtn.addEventListener('click', () => {
            submitChangePasswordForm()
        })
    }

    const forgotPasswordBtn = document.getElementById('forgotPasswordButton')
    if (forgotPasswordBtn) {
        forgotPasswordBtn.addEventListener('click', () => {
            submitForgotPasswordForm()
        })
    }

    const loginBtn = document.getElementById('loginButton')
    if (loginBtn) {
        loginBtn.addEventListener('click', () => {
            submitLoginForm()
        })
    }

})


function submitRegisterForm() {
    const formData = new FormData;
    const registerBtn = document.getElementById('registerButton')

    registerBtn.disabled = true

    formData.append('name',document.getElementById('name').value)
    formData.append('phoneNumber',document.getElementById('phoneNumber').value)
    formData.append('dateOfBirth',document.getElementById('dateOfBirth').value)
    formData.append('bio',document.getElementById('bio').value)
    formData.append('email',document.getElementById('email').value)
    formData.append('password',document.getElementById('password').value)

    axios.post('/api/user/register', formData)
        .then(response => {
            if (response.status === 201) {
                showSuccess(response.data)
            } else {
                showError(response.data)
                registerBtn.disabled = false
            }
        })
        .catch(error => {
            registerBtn.disabled = false
            showError(error.response.data)
        })
}

function submitChangePasswordForm(){
    const email= document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const password2 = document.getElementById('password2').value;

    const changePasswordBtn = document.getElementById('changePasswordButton');
    changePasswordBtn.disabled = true
    axios.post('/api/user/change-password', {
        email: email,
        password: password,
        password2: password2
    })
        .then(response => {
            showSuccess(response.data)
        })
        .catch(error => {
            showError(error.response.data)
            changePasswordBtn.disabled = false
        })
}

function submitForgotPasswordForm() {
    const email = document.getElementById('email').value;
    const forgotPasswordBtn = document.getElementById('forgotPasswordButton');

    //Disable the button for 30s

    forgotPasswordBtn.disabled = true
    forgotPasswordBtn.innerText = "Vui lòng chờ 30 giây"

    axios.post('/api/user/forgot-password', null, {
        params: { email: email }
    })
        .then(response => {
            showSuccess(response.data)
        })
        .catch(error => {
            showError(error.response.data)
        })
        .finally( () => {
            setTimeout( () => {
                forgotPasswordBtn.disabled = false
                forgotPasswordBtn.innerText = "Gửi link đổi mật khẩu"
            }, 30000)
        })
}

function submitLoginForm() {
    const email = document.getElementById('email').value
    const password = document.getElementById('password').value

    axios.post('/api/user/login', {
        email: email,
        password: password
    })
        .then(response => {
            window.location.href = "/user"
        })
        .catch(error => {
            showError(error.response.data)
            password.value = "";
        })
}
