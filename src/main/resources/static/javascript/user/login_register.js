
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

    const name = document.getElementById('name').value
    const phoneNumber = document.getElementById('phoneNumber').value
    const dateOfBirth = document.getElementById('dateOfBirth').value
    const bio = document.getElementById('bio').value
    const email = document.getElementById('email').value
    const password = document.getElementById('password').value

    if (!checkNamePhoneNumber(name, phoneNumber)) {
        registerBtn.disabled = false;
        return;
    }

    if (!checkEmailPassword(email, password)) {
        registerBtn.disabled = false;
        return;
    }

    formData.append('name', name);
    formData.append('phoneNumber', phoneNumber);
    formData.append('dateOfBirth', dateOfBirth);
    formData.append('bio', bio);
    formData.append('email', email);
    formData.append('password', password);

    axios.post('/api/user/register', formData)
        .then(response => {
            if (response.status === 201) {
                showSuccess(response.data, 30000)
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

    if (!checkEmailPassword(email, password)) {
        return;
    }
    if (password !== password2) {
        showError("Mật khẩu xác nhận chưa đúng")
        return
    }
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

    if(!checkEmailPassword(email,password)){
        return
    }
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

function checkNamePhoneNumber(name, phoneNumber) {
    if (name.trim() === '') {
        showError('Tên không được bỏ trống')
        return false
    }

    const phoneRegex = /^0[1-9][0-9]{8}$/
    if (!phoneRegex.test(phoneNumber)) {
        showError('Số điện thoại không hợp lệ')
        return false
    }
    return true
}

function checkEmailPassword(email, password) {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
    if (email.trim() === '') {
        showError('Email không được trống')
        return false
    } else if (!emailRegex.test(email)) {
        showError('Email không hợp lệ')
        return false
    }

    if (password.trim() === '') {
        showError('Mật khẩu không được trống')
        return false
    } else if (password.length < 6) {
        showError('Mật khẩu yêu cầu ít nhất 6 ký tự')
        return false
    }
    return true
}